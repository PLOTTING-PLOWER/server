package com.plotting.server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("http://localhost:8080"); // 로컬 호스 개방
        config.addAllowedMethod("*");       // 모든 HTTP 메서드 허용 (GET, POST 등)
        config.addAllowedHeader("*");       // 모든 헤더 허용
        config.setAllowCredentials(true);   // 인증 정보 허용 (Authorization 헤더 포함)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 설정 적용
        return new CorsFilter(source);
    }
}
