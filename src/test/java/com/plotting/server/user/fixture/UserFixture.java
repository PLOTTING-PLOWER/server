package com.plotting.server.user.fixture;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import org.springframework.test.util.ReflectionTestUtils;

import static com.plotting.server.user.domain.UserType.LoginType.SELF;

public class UserFixture {
    public static final User USER = User.builder()
            .nickname("유저")
            .profileImageUrl("https://profile.com")
            .profileMessage("프로필 메시지")
            .loginType(SELF)
            .isAlarmAllowed(true)
            .isProfilePublic(true)
            .role(Role.USER)
            .fcmToken("fcmToken")
            .build();

    static {
        ReflectionTestUtils.setField(USER, "id", 1L);
    }
}