package com.co.kr.coreJpa.sys.api1.api1Service;

import com.co.kr.coreJpa.sys.api1.api1Entity.Api1Entity;
import com.co.kr.coreJpa.sys.api1.api1Repository.Api1Repository;
import com.co.kr.coreJpa.sys.api1.dto.Api1Dto;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Api1Service {
    private final Api1Repository api1Repository;

    public List<Api1Dto> getAllConfigs(Api1Dto cond) {
        Specification<Api1Entity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. DTO가 통째로 null일 경우를 대비한 안전장치
            if (cond == null) return cb.and(predicates.toArray(new Predicate[0]));

            // 2. 서비스단에서 직접 null과 빈 문자열을 검증하여 조건 생성
            if (cond.apiName() != null && !cond.apiName().isBlank()) {
                predicates.add(cb.like(root.get("apiName"), "%" + cond.apiName() + "%"));
            }

            if (cond.method() != null && !cond.method().isBlank()) {
                predicates.add(cb.equal(root.get("method"), cond.method()));
            }

            // 추가적인 필드(endpoint 등)도 여기서 동일하게 처리 가능
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return api1Repository.findAll(spec).stream()
                .map(Api1Dto::from)
                .toList();
    }
}