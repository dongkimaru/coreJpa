package com.co.kr.coreJpa.module.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiConfigInterceptor implements HandlerInterceptor {

    @Getter
    @Builder
    public static class ApiConfig {
        private String apiKey;
        private String authToken;
    }

    private static final ThreadLocal<ApiConfig> STORAGE = new ThreadLocal<>();

    public static ApiConfig getHeader() { return STORAGE.get(); }
    // ------------------------------------------

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ApiConfig info = ApiConfig.builder()
                .apiKey(request.getHeader("X-API-KEY"))
                .authToken(request.getHeader("Authorization"))
                .build();

        STORAGE.set(info);

        response.setContentType("application/json;charset=UTF-8");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        STORAGE.remove();
    }
}
