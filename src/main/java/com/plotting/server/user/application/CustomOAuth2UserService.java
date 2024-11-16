package com.plotting.server.user.application;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.OAuthAttributes;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService <OAuth2UserRequest, OAuth2User>{      // 로그인 시 사용자 정보 처리 역할 함수
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();
        log.info("------loadUser------");
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 사용자 정보 매핑
        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 구글, 네이버
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println("registrationId: " + registrationId);
        System.out.println("attributes: " + oAuth2User.getAttributes());

        // 사용자 정보 매핑
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        System.out.println("Mapped Attributes: " + attributes.getAttributes());

        // 사용자 정보 저장 또는 업데이트
        User user = saveOrUpdate(attributes);
        log.info("------user------"+ user);
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2User.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());
//        user.update(attributes.getName(), attributes.getPicture());    굳이 필요하지 않을것같아서!!!
        return userRepository.save(user);
    }
}
