package com.fitness.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini_api_url}")
    private String GeminiApiUrl;
    @Value("${gemini_api_key}")
    private String GeminiApiKey;
    private final WebClient webClient;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String question){
        Map<String,Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
        }
        );

        String  response = webClient.post()
                .uri(GeminiApiUrl+GeminiApiKey) // final url formation
                .header("Content-Type","application/json")// gemini ko batata h body JSON format me h
                .bodyValue(requestBody)//👉 Map → JSON me convert ho jaata hai automatically
                .retrieve()//request send
                .bodyToMono(String.class)//async response
                .block();//async ko sync bana deta hai
        return response;
    }
}
