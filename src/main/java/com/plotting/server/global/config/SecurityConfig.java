package com.plotting.server.global.config;

import com.plotting.server.global.filter.JwtAuthenticationFilter;
import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.application.CustomOAuth2UserService;
import com.plotting.server.user.application.UserDetailsServiceImpl;
import com.plotting.server.user.presentation.CustomAuthenticationSuccessHandler;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration; // 추가

    private final String[] WHITE_LIST = {
            "/error",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/*",
            "/webjars/**",
            "/auth/**",
            "/global/health-check"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)        // CSRF 보호 비활성화 (REST API를 위한 설정)
                .authorizeHttpRequests(auth -> auth    // 권한 설정
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().permitAll()
//                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화 (JWT 사용)
                // JWT 인증 필터
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth2/authorize") // Google/Naver 인증 요청 경로 설정
                        )
                        .redirectionEndpoint(redirect -> redirect
                                .baseUri("/login/oauth2/code/*") // 인증 후 리디렉션 설정
                        )
                        .userInfoEndpoint(userInfo->userInfo
                                .userService(customOAuth2UserService())         // 사용자 정보 처리
                        )
                        .successHandler(customAuthenticationSuccessHandler())   // 성공 시 JWT 발급
                        .failureUrl("/auth/login?error")        // 실패 시 이동 경로 -> 아직 따로 처리는 안하긴 함
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {      // Swagger 경로를 완전히 필터링에서 제외
        return web -> web.ignoring()
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService(userRepository);
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(userRepository, jwtUtil);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}