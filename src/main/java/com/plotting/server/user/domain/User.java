package com.plotting.server.user.domain;

import com.plotting.server.global.domain.BaseTimeEntity;
import com.plotting.server.user.domain.UserType.LoginType;
import com.plotting.server.user.domain.UserType.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "profile_message", nullable = false)
    private String profileMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Column(name = "is_alarm_allowed", nullable = false)
    private Boolean isAlarmAllowed;

    @Column(name = "is_profile_public", nullable = false)
    private Boolean isProfilePublic;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;

    @Builder
    public User(String nickname, String email, String password, String socialId, String profileImageUrl,
                String profileMessage, LoginType loginType, Boolean isAlarmAllowed, Boolean isProfilePublic,
                Role role, String fcmToken) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.socialId = socialId;
        this.profileImageUrl = profileImageUrl;
        this.profileMessage = profileMessage;
        this.loginType = loginType;
        this.isAlarmAllowed = isAlarmAllowed;
        this.isProfilePublic = isProfilePublic;
        this.role = role;
        this.fcmToken = fcmToken;
    }

    public static User createTestUser(String nickname) {
        return User.builder()
                .nickname(nickname)    // 기본값 설정
                .email("Testemail@test.com")
                .role(Role.USER)
                .password("test_password")   // 임시 비밀번호
                .socialId("test_social_id")  // 임시 소셜 ID
                .profileImageUrl("test_url") // 기본 프로필 이미지
                .profileMessage("Hello World!") // 기본 프로필 메시지
                .loginType(LoginType.SELF)   // 기본 로그인 타입
                .isAlarmAllowed(true)        // 알람 허용
                .isProfilePublic(true)       // 프로필 공개 여부
                .fcmToken("test_fcm_token")  // 임시 FCM 토큰
                .build();
    }

}
