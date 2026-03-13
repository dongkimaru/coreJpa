package com.co.kr.coreJpa.module.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ApiLoggingAspect {

    @Around("execution(* com.co.kr.coreJpa..*Controller.*(..))")
    public Object logWithIp(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip = getClientIp(request);
        String url = request.getRequestURI();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        long start = System.currentTimeMillis();

        // [BEFORE] 로그에 IP 정보 추가
        log.info(">>>> [REQ] IP: {} | URL: {} | Method: {} | Params: {}",
                ip, url, methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;

            // [AFTER] 성공 로그
            log.info("<<<< [RES] IP: {} | URL: {} | Time: {}ms", ip, url, executionTime);
            return result;
        } catch (Exception e) {
            log.error("!!!! [ERR] IP: {} | URL: {} | Message: {}", ip, url, e.getMessage());
            throw e;
        }
    }

    // 프록시 환경에서도 실제 클라이언트 IP를 가져오는 메서드
    private String getClientIp(HttpServletRequest request) {
        String[] headerNames = {
                "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headerNames) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0]; // 여러 개일 경우 첫 번째가 실제 클라이언트
            }
        }
        return request.getRemoteAddr();
    }
}