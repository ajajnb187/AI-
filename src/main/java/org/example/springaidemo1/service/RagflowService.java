package org.example.springaidemo1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springaidemo1.dto.RagflowDtos.RagflowApiResponse;
import org.example.springaidemo1.dto.RagflowDtos.RagflowChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

@Service
public class RagFlowService {

    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${ragflow.api-url}")
    private String ragflowApiUrl;

    @Value("${ragflow.api-key}")
    private String ragflowApiKey;

    public RagFlowService(RestTemplate restTemplate, WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    /**
     * 调用 RagFlow 聊天接口 - 普通对话模式
     * 
     * @param request 聊天请求
     * @return 聊天响应
     */
    public RagflowApiResponse chat(RagflowChatRequest request) {
        try {
            var headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(ragflowApiKey);
            var entity = new org.springframework.http.HttpEntity<>(request, headers);
            var response = restTemplate.postForEntity(ragflowApiUrl, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readValue(response.getBody(), RagflowApiResponse.class);
            }
            RagflowApiResponse error = new RagflowApiResponse();
            error.setCode(response.getStatusCode().value());
            error.setMessage("HTTP " + response.getStatusCode());
            return error;
        } catch (HttpStatusCodeException e) {
            RagflowApiResponse error = new RagflowApiResponse();
            error.setCode(e.getStatusCode().value());
            error.setMessage(e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return error;
        } catch (Exception e) {
            RagflowApiResponse error = new RagflowApiResponse();
            error.setCode(500);
            error.setMessage(e.getMessage());
            return error;
        }
    }

    /**
     * 调用 RagFlow 聊天接口 - 流式对话模式
     * 
     * @param request 聊天请求
     * @return 流式聊天响应
     */
    public Flux<String> chatStream(RagflowChatRequest request) {
        request.setStream(Boolean.TRUE);
        return webClient
                .post()
                .uri(ragflowApiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ragflowApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM, MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(String.class)
                .map(String::trim)
                .filter(s -> !s.isEmpty());
    }
}