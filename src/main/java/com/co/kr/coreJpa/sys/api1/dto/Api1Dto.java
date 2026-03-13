package com.co.kr.coreJpa.sys.api1.dto;

import com.co.kr.coreJpa.sys.api1.api1Entity.Api1Entity;
import java.time.LocalDateTime;

public record Api1Dto(
        String apiName,
        String endpoint,
        String method,
        LocalDateTime createdAt
) {
    public static Api1Dto from(Api1Entity entity) {
        return new Api1Dto(
                entity.getApiName(),
                entity.getEndpoint(),
                entity.getMethod(),
                entity.getCreatedAt()
        );
    }
}