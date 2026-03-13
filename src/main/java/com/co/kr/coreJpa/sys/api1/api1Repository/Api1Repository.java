package com.co.kr.coreJpa.sys.api1.api1Repository;

import com.co.kr.coreJpa.sys.api1.api1Entity.Api1Entity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface Api1Repository extends JpaRepository<Api1Entity, Long>, JpaSpecificationExecutor<Api1Entity> {
    @Override
    List<Api1Entity> findAll(Specification<Api1Entity> spec);
}
