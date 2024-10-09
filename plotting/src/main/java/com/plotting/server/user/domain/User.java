package com.plotting.server.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "social_id", length = 255)
    private String socialId;

    @Column(name = "profile_image_url", nullable = false, length = 1000)
    private String profileImageUrl;

    @Column(name = "profile_message", nullable = false, length = 1000)
    private String profileMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "is_alarm_allowed", nullable = false)
    private boolean isAlarmAllowed;

    @Column(name = "is_profile_public", nullable = false)
    private boolean isProfilePublic;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "rank", nullable = false)
    private Long rank;

    @Column(name = "fcm_token", nullable = false, length = 1000)
    private String fcmToken;

    @Builder
    public User(String nickname, String email, String password, String socialId, String profileImageUrl,
                String profileMessage, Provider provider, boolean isAlarmAllowed, boolean isProfilePublic,
                Role role, LocalDateTime createdDate, Long rank, String fcmToken) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.socialId = socialId;
        this.profileImageUrl = profileImageUrl;
        this.profileMessage = profileMessage;
        this.provider = provider;
        this.isAlarmAllowed = isAlarmAllowed;
        this.isProfilePublic = isProfilePublic;
        this.role = role;
        this.createdDate = createdDate;
        this.rank = rank;
        this.fcmToken = fcmToken;
    }

    public enum Provider {
        NAVER, GOOGLE, SELF
    }

    public enum Role {
        GUEST, USER, ADMIN
    }
}
