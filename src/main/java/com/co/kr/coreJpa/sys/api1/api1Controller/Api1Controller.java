package com.co.kr.coreJpa.sys.api1.api1Controller;

import com.co.kr.coreJpa.module.common.CommonResponse;
import com.co.kr.coreJpa.module.common.ResponseCode;
import com.co.kr.coreJpa.sys.api1.api1Entity.Api1Entity;
import com.co.kr.coreJpa.sys.api1.api1Service.Api1Service;
import com.co.kr.coreJpa.sys.api1.dto.Api1Dto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "API Configuration", description = "시스템 API 설정 관리 및 조회")
@RestController
@RequiredArgsConstructor
public class Api1Controller {

    private final Api1Service api1Service;

    @Operation(summary = "전체 API 설정 조회")
    @PostMapping("/api/v1/configs")
    public ResponseEntity<CommonResponse<List<Api1Dto>>> getApiConfigs(
            @RequestBody Api1Dto cond // Body에서 DTO를 통째로 받음
    ) {
        // Specification 로직이 담긴 서비스 호출
        List<Api1Dto> data = api1Service.getAllConfigs(cond);

        return ResponseEntity.ok(CommonResponse.of(ResponseCode.SUCCESS, data));
    }

}
