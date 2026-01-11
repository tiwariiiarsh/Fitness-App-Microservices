package com.example.activityService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//------------------>> Use of @LoadBalanced  <<------------------
//1️⃣ Isse tum service-name se call kar sakte ho (IP/port nahi likhna padta).
// 2️⃣ Agar ek service ke multiple instances hain, to request automatically distribute hoti hai.
//3️⃣ Ye client-side load balancing karta hai (Spring Cloud LoadBalancer).

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webclientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient userServiceWebClient(WebClient.Builder  webClientBuilder){
        return webClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build(); 
    }
}
