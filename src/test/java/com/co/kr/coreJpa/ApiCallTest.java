package com.co.kr.coreJpa;

import com.co.kr.coreJpa.module.common.CommonResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiCallTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("DTO 객체 없이 날것의 JSON을 POST 바디에 실어 보낸다")
    void realJsonApiTest() {
        String jsonRequest = """
            {
                "apiName": "date",
                "method": ""
            }
            """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/configs",
                entity,
                String.class
        );


        String jsonBody = response.getBody();
        System.out.println("==================== [실제 네트워크 통신 응답] ====================");
        System.out.println(jsonBody);
        System.out.println("================================================================");

    }
}