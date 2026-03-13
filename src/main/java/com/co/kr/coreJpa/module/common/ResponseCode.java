package com.co.kr.coreJpa.module.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    // 성공 (200 계열)
    SUCCESS("0", "정상적으로 처리되었습니다.", HttpStatus.OK),

    // 클라이언트 오류 (400 계열)
    BAD_REQUEST("400", "잘못된 요청 파라미터입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("404", "요청하신 자원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("401", "인증 정보가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),

    // 서버 오류 (500 계열)
    SERVER_ERROR("500", "시스템 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR("501", "데이터베이스 처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CRYPTO_ERROR("502", "보안 복호화 과정에서 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ResponseCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
