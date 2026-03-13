package com.co.kr.coreJpa.sys.api1.api1Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_configs")
@Getter
@NoArgsConstructor
public class Api1Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_name", nullable = false, length = 100)
    private String apiName;

    @Column(name = "endpoint", nullable = false, length = 255)
    private String endpoint;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "procedure_name", length = 100)
    private String procedureName;

    @Column(name = "content_type", length = 50)
    private String contentType;

    @Column(name = "auth_level", length = 20)
    private String authLevel;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}