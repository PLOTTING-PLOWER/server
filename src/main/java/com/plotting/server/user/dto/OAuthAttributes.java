package com.plotting.server.user.dto;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.LoginType;
import com.plotting.server.user.domain.UserType.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {
    // OAuth2User의 속성을 파싱, User 엔티티로 변환
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String email;
    private String socialId;
    private String picture;
    private LoginType loginType;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        LoginType loginType = Arrays.stream(LoginType.values())
                .filter(type -> type.getLoginType().equalsIgnoreCase(registrationId)) // 대소문자 구분 없이 비교
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported loginType: " + registrationId));
        if(loginType == LoginType.GOOGLE) {
            return ofGoogle(userNameAttributeName, attributes);
        }else if (loginType == LoginType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        throw new IllegalArgumentException("Unknown registration id " + registrationId);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .socialId((String) attributes.get("sub"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .loginType(LoginType.GOOGLE)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) {
            throw new IllegalArgumentException("Missing attribute 'response' in attributes");
        }
        return OAuthAttributes.builder()
                .nickname((String) response.get("nickname"))
                .email((String) response.get("email"))
                .socialId((String) response.get("id"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .loginType(LoginType.NAVER)     // 로그인 타입 설정
                .build();
    }

    public User toUser(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .socialId(socialId)
                .profileImageUrl(picture)
                .loginType(loginType)
                .role(Role.USER)
                .fcmToken("z")
                .isAlarmAllowed(true) // 기본값 명시적으로 설정
                .isProfilePublic(true) // 기본값 설정
                .profileMessage("Welcome!") // 기본 메시지 설정
                .build();
    }
}
