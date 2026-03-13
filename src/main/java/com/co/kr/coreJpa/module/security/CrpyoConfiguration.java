package com.co.kr.coreJpa.module.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class CrpyoConfiguration implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Spring Context에서 현재 애플리케이션의 환경 설정 정보를 담당하는 객체를 가져옴
        ConfigurableEnvironment env = beanFactory.getBean(ConfigurableEnvironment.class);

        // 환경 설정 정보 중에서 설정 값의 소스들을 관리하는 객체를 가져옵니다.
        MutablePropertySources propertySources = env.getPropertySources();

        // 설정 파일(Source)에서 마스터 키와 암호화 값 가져옴
        String masterKey = env.getProperty("jasypt.encryptor.password");
        String encryptedUrl = env.getProperty("spring.datasource.url");
        String encryptedUser = env.getProperty("spring.datasource.username");
        String encryptedPw = env.getProperty("spring.datasource.password");

        // 마스터 키가 없으면 복호화 진행 불가 (보안상 필수 체크)
        if (masterKey == null || masterKey.isEmpty()) {
            throw new RuntimeException("Master key (jasypt.encryptor.password) is missing!");
        }

        try {
            // 복호화된 값들을 담을 임시 저장소(Map) 생성
            Map<String, Object> decryptedProps = new HashMap<>();

            // Crypto 유틸리티를 호출하여 암호문을 평문으로 변환 (AES-256 복호화)
            decryptedProps.put("spring.datasource.url", Crypto.decrypt(encryptedUrl, masterKey));
            decryptedProps.put("spring.datasource.username", Crypto.decrypt(encryptedUser, masterKey));
            decryptedProps.put("spring.datasource.password", Crypto.decrypt(encryptedPw, masterKey));

            // [중요] 복호화된 값들을 새로운 'PropertySource'로 만들어서 설정 우선순위의 '맨 앞(addFirst)'에 배치
            // 이렇게 하면 기존 application.properties에 있던 암호화된 값보다 복호화된 값이 먼저 읽히게 됨
            propertySources.addFirst(new MapPropertySource("decryptedProperties", decryptedProps));

            System.out.println(">>> [coreJpa] Security module: Database credentials decrypted.");

        } catch (Exception e) {
            String errorMsg = String.format("[보안 모듈 에러] 데이터베이스 설정 복호화에 실패하였습니다", e.getMessage());

            throw new RuntimeException(errorMsg, e);
        }
    }
}
