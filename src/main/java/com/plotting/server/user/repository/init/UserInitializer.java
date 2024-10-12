package com.plotting.server.user.repository.init;

import com.plotting.server.global.util.DummyDataInit;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

import static com.plotting.server.user.domain.UserType.LoginType.SELF;
import static com.plotting.server.user.domain.UserType.Role.ADMIN;
import static com.plotting.server.user.domain.UserType.Role.USER;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            User DUMMY_USER1 = User.builder()
                    .nickname("user1")
                    .email("user1@email.com")
                    .password("password1")
                    .profileImageUrl("profileImage1.png")
                    .profileMessage("플로깅 열심히 하는 1인입니다 :)")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER2 = User.builder()
                    .nickname("user2")
                    .email("user2@email.com")
                    .password("password2")
                    .profileImageUrl("profileImage2.png")
                    .profileMessage("플로깅 자주 해요!!")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_ADMIN = User.builder()
                    .nickname("admin")
                    .email("admin@email.com")
                    .password("adminPassword")
                    .profileImageUrl("profileImage3.png")
                    .profileMessage("관리자 계정입니다")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(ADMIN)
                    .fcmToken("fcmToken")
                    .build();

            userList.add(DUMMY_USER1);
            userList.add(DUMMY_USER2);
            userList.add(DUMMY_ADMIN);

            userRepository.saveAll(userList);
        }
    }
}