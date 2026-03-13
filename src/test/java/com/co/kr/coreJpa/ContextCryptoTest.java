package com.co.kr.coreJpa;

import org.junit.jupiter.api.Test;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class ContextCryptoTest {

    private static final String ALGORITHM = "AES";

    @Test
    void createCrypto() throws Exception {

        // pplication.properties에서 encryptor.password 읽기
        String masterKey = loadMasterKeyFromProperties();

        Map<String, String> targetMap = new LinkedHashMap<>();
        targetMap.put("url", "jdbc:mysql://localhost:3306/demo?serverTimezone=Asia/Seoul");
        targetMap.put("username", "root");
        targetMap.put("password", "dongyang");

        // 2. 마스터 키를 SHA-256으로 해싱하여 AES-256용 32바이트 키 생성
        byte[] hashedKey = generateHashedKey(masterKey);
        SecretKeySpec keySpec = new SecretKeySpec(hashedKey, ALGORITHM);

        System.out.println("=== [ Encrypted Properties for coreJpa ] ===");
        System.out.println("Master Key from properties: " + masterKey);

        for (Map.Entry<String, String> entry : targetMap.entrySet()) {
            String encryptedValue = encrypt(entry.getValue(), keySpec);
            System.out.println(entry.getKey() + "=" + encryptedValue);
        }

        System.out.println("==========================================");
    }

    private String loadMasterKeyFromProperties() throws Exception {
        Properties properties = new Properties();

        // 클래스패스에서 application.properties 파일 로드
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties 파일을 찾을 수 없습니다!");
            }

            properties.load(input);
        }

        String masterKey = properties.getProperty("encryptor.password");

        if (masterKey == null || masterKey.isEmpty()) {
            throw new RuntimeException("encryptor.password 값이 없습니다!");
        }

        return masterKey;
    }

    // SHA-256을 통해 어떤 길이의 키든 32바이트로 변환
    private byte[] generateHashedKey(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    private String encrypt(String plainText, SecretKeySpec keySpec) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}