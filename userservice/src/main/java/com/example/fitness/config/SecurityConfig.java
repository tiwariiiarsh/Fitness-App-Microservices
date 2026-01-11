package com.example.fitness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF (REST APIs ke liye)
                .csrf(csrf -> csrf.disable())

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // 🔓 OPEN endpoints (Gateway use karta hai)
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/*/validate"
                        ).permitAll()

                        // 🔒 Baaki sab secure
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
