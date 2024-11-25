package com.plotting.server.user.repository.init;

import com.plotting.server.global.util.DummyDataInit;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cloud.aws.s3.endpoint}")
    private String s3Endpoint;

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            User DUMMY_USER1 = User.builder()
                    .nickname("슝슝이")
                    .email("user1@email.com")
                    .password("password1")
                    .profileImageUrl(s3Endpoint + "/profile/soong.png")
                    .profileMessage("플로깅 열심히 하는 1인입니다 :)")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER2 = User.builder()
                    .nickname("피치")
                    .email("user2@email.com")
                    .password("password2")
                    .profileImageUrl(s3Endpoint + "/profile/apeach.png  ")
                    .profileMessage("플로깅 자주 해요!!")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER3 = User.builder()
                    .nickname("라이언")
                    .email("user3@email.com")
                    .password("password3")
                    .profileImageUrl(s3Endpoint + "/profile/lion.jpg")
                    .profileMessage("동작구 플로깅 전문가 입니다")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER4 = User.builder()
                    .nickname("무민")
                    .email("user4@email.com")
                    .password("password4")
                    .profileImageUrl(s3Endpoint + "/profile/moomin.jpg")
                    .profileMessage("플로깅하면서 친해질 분들 즐겨찾기 해주세요!")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER5 = User.builder()
                    .nickname("버즈")
                    .email("user5@email.com")
                    .password("password5")
                    .profileImageUrl(s3Endpoint + "/profile/buz.jpg")
                    .profileMessage("주로 주말에 플로깅 합니다. 같이 해요!")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER6 = User.builder()
                    .nickname("user6")
                    .email("user6@email.com")
                    .password("password6")
                    .profileImageUrl("profileImage6.png")
                    .profileMessage("플로깅은 나의삶")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER7 = User.builder()
                    .nickname("user7")
                    .email("user7@email.com")
                    .password("password7")
                    .profileImageUrl("profileImage7.png")
                    .profileMessage("플로깅과 함께라면 어디든지..")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER8 = User.builder()
                    .nickname("user8")
                    .email("user8@email.com")
                    .password("password8")
                    .profileImageUrl("profileImage8.png")
                    .profileMessage("플로깅 모두 모여")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER9 = User.builder()
                    .nickname("user9")
                    .email("user9@email.com")
                    .password("password9")
                    .profileImageUrl("profileImage9.png")
                    .profileMessage("유럽 여행 대신 플로깅 여행")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();

            User DUMMY_USER10 = User.builder()
                    .nickname("user10")
                    .email("user10@email.com")
                    .password("password10")
                    .profileImageUrl("profileImage10.png")
                    .profileMessage("기분이 좋다 플로깅 하니까")
                    .loginType(SELF)
                    .isAlarmAllowed(true)
                    .isProfilePublic(true)
                    .role(USER)
                    .fcmToken("fcmToken")
                    .build();



            User DUMMY_ADMIN = User.builder()
                    .nickname("관리자")
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
            userList.add(DUMMY_USER6);
            userList.add(DUMMY_USER7);
            userList.add(DUMMY_USER8);
            userList.add(DUMMY_USER9);
            userList.add(DUMMY_USER10);
            userList.add(DUMMY_ADMIN);

            userRepository.saveAll(userList);
        }
    }
}