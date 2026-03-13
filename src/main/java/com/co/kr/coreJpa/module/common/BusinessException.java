package com.co.kr.coreJpa.module.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ResponseCode responseCode;

    // Enum에 정의된 기본 메시지를 그대로 사용할 때
    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    // 상황에 따라 상세한 에러 메시지를 추가하고 싶을 때
    public BusinessException(ResponseCode responseCode, String customMessage) {
        super(customMessage);
        this.responseCode = responseCode;
    }
}
