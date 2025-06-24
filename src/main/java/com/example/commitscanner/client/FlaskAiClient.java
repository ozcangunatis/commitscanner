package com.example.commitscanner.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class FlaskAiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FLASK_URL = "http://localhost:5000/analyze";

    public Map<String, Object> analyzeCommit(String message, String diff) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("message", message);
        requestBody.put("diff", diff);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(FLASK_URL, request, Map.class);
        return response.getBody();
    }
}
