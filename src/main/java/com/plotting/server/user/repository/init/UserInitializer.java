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

            User DUMMY_USER3 = User.builder()
                    .nickname("user3")
                    .email("user3@email.com")
                    .password("password3")
                    .profileImageUrl("profileImage3.png")
                    .profileMessage("동작구 플로깅 전문가 입니다")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER4 = User.builder()
                    .nickname("user4")
                    .email("user4@email.com")
                    .password("password4")
                    .profileImageUrl("profileImage4.png")
                    .profileMessage("플로깅하면서 친해질 분들 즐겨찾기 해주세요!")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER5 = User.builder()
                    .nickname("user5")
                    .email("user5@email.com")
                    .password("password5")
                    .profileImageUrl("profileImage5.png")
                    .profileMessage("주로 주말에 플로깅 합니다. 같이 해요!")
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
                    .profileImageUrl("profileImageAdmin.png")
                    .profileMessage("관리자 계정입니다")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(ADMIN)
                    .fcmToken("fcmToken")
                    .build();

            userList.add(DUMMY_USER1);
            userList.add(DUMMY_USER2);
            userList.add(DUMMY_USER3);
            userList.add(DUMMY_USER4);
            userList.add(DUMMY_USER5);
            userList.add(DUMMY_ADMIN);

            userRepository.saveAll(userList);
        }
    }
}