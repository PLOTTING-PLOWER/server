package com.plotting.server.user.application;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import com.plotting.server.user.dto.OAuthAttributes;
import com.plotting.server.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static com.google.firebase.database.util.JsonMapper.parseJson;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService <OAuth2UserRequest, OAuth2User>{      // 로그인 시 사용자 정보 처리 역할 함수
    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUrl;
    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();
        log.info("------loadUser------");
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 사용자 정보 매핑
        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 구글, 네이버
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //log.info("registrationId: " + registrationId);
        //log.info("attributes: " + oAuth2User.getAttributes());

        // 사용자 정보 매핑
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        //log.info("Mapped Attributes: " + attributes.getAttributes());

        // 사용자 정보 저장 또는 업데이트
        User user = saveOrUpdate(attributes);
        //log.info("------user------"+ user);
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2User.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 안드로이드에서 전송된 네이버 토큰 처리 메서드 추가
    public User loadUserFromToken(String accessToken){
        // WebClient 인스턴스 생성
        WebClient webClient = webClientBuilder.baseUrl(naverUserInfoUrl).build();

        // API 요청 보내기
        String response = webClient.get()
                .uri("")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 응답 처리
        Map<String, Object> responseBody = null;
        try {
            responseBody = parseJson(response);
        } catch (IOException e) {
            log.error("IOException occurred while parsing JSON", e);
        }
        OAuthAttributes oauthAttributes = OAuthAttributes.ofNaver("id", responseBody);

        // 사용자 정보 저장 또는 업데이트
        return saveOrUpdate(oauthAttributes);
    }



    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(existingUser -> {
                    // 탈퇴 상태라면 상태를 활성화로 변경
                    if (existingUser.getRole() == Role.WITHDRAWN) {
                        existingUser.updateRole(Role.USER);
                    }
                    // 기존 정보를 업데이트 (필요한 경우 추가 필드를 업데이트 가능)
                    return existingUser;
                })
                .orElse(attributes.toUser());
//        user.update(attributes.getName(), attributes.getPicture());    굳이 필요하지 않을것같아서!!!
        return userRepository.save(user);
    }
}
