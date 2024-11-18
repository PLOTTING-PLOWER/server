package com.plotting.server.user.presentation;

import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.LoginType;
import com.plotting.server.user.domain.UserType.Role;
import com.plotting.server.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 인증 성공 시
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

        // 제공자 정보 확인 (Google, Naver)
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        String socialId;
        if("google".equals(registrationId)) {
            // 사용자 정보 추출
            socialId = (String) attributes.get("sub");
        }else if("naver".equals(registrationId)) {
            Map<String, Object> responseMap = (Map<String, Object>) attributes.get("response");
            socialId = (String) responseMap.get("id");
        }else {
            throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        }

        // 사용자 찾아서
        User user = userRepository.findBySocialId(socialId).orElseThrow();

        //Jwt 토큰 생성
        String token = jwtUtil.generateToken(user);

        // 클라이언트에 토큰 전달
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("{\"token\": \"" + token + "\", \"message\": \"Login successful\"}");
    }

}
