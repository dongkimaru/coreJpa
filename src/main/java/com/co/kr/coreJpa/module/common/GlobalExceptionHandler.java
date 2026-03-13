package com.co.kr.coreJpa.module.common;

import com.co.kr.coreJpa.module.common.CommonResponse;
import com.co.kr.coreJpa.module.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Object>> handleBusinessException(BusinessException e) {
        ResponseCode responseCode = e.getResponseCode();

        log.error(">>>> [BIZ ERROR] Code: {}, Message: {}", responseCode.getCode(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail(responseCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResponse<Object>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        log.error(">>>> [HTTP ERROR] 잘못된 메서드 요청: {}", e.getMethod());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(CommonResponse.fail("405", "지원하지 않는 HTTP 메서드입니다."));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<Object>> handleBadRequest(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail("400", "요청 바디 형식이 잘못되었습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleAllException(Exception e) {
        log.error("!!!! [CRITICAL ERROR] {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail("500", "서버 내부 오류가 발생했습니다."));
    }
}