package com.co.kr.coreJpa.module.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CommonResponse<T>(
    String Code,
    String Message,
    T data,
    String timestamp
) {

    // 2개짜리: Enum의 기본 메시지 사용
    public static <T> CommonResponse<T> of(ResponseCode responseCode, T data) {
        return of(responseCode, responseCode.getMessage(), data);
    }

    // 메시지를 직접 전달 (e.getMessage() 등을 담을 때 사용)
    public static <T> CommonResponse<T> of(ResponseCode responseCode, String customMessage, T data) {
        return new CommonResponse<>(
                responseCode.getCode(),
                customMessage,
                data,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

    public static <T> CommonResponse<T> success(T data) {
        return of(ResponseCode.SUCCESS, data);
    }

    public static <T> CommonResponse<T> fail(String errorCode, String errorMessage) {
        return new CommonResponse<>(
                errorCode,
                errorMessage,
                null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

}
