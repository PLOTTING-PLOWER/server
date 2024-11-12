package com.plotting.server.user.repository.init;

import com.plotting.server.global.util.DummyDataInit;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserStar;
import com.plotting.server.user.repository.UserRepository;
import com.plotting.server.user.repository.UserStarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class UserStarInitializer implements ApplicationRunner {
    private final UserStarRepository userStarRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userStarRepository.count() > 0) {
            log.info("[UserStar]더미 데이터 존재");
        } else {
            User DUMMY_USER1 = userRepository.findById(1L).orElseThrow();
            User DUMMY_USER2 = userRepository.findById(2L).orElseThrow();
            User DUMMY_USER3 = userRepository.findById(3L).orElseThrow();
            User DUMMY_USER4 = userRepository.findById(4L).orElseThrow();
            User DUMMY_USER5 = userRepository.findById(5L).orElseThrow();
            User DUMMY_USER6 = userRepository.findById(6L).orElseThrow();
            User DUMMY_USER7 = userRepository.findById(7L).orElseThrow();
            User DUMMY_USER8 = userRepository.findById(8L).orElseThrow();
            User DUMMY_USER9 = userRepository.findById(9L).orElseThrow();
            User DUMMY_USER10 = userRepository.findById(10L).orElseThrow();


            List<UserStar> userStarList = new ArrayList<>();

            UserStar DUMMY_USER_STAR1 = UserStar.builder()
                    .user(DUMMY_USER1)
                    .starUser(DUMMY_USER3)
                    .build();

            UserStar DUMMY_USER_STAR2 = UserStar.builder()
                    .user(DUMMY_USER2)
                    .starUser(DUMMY_USER1)
                    .build();

            UserStar DUMMY_USER_STAR3 = UserStar.builder()
                    .user(DUMMY_USER3)
                    .starUser(DUMMY_USER1)
                    .build();

            UserStar DUMMY_USER_STAR4 = UserStar.builder()
                    .user(DUMMY_USER4)
                    .starUser(DUMMY_USER1)
                    .build();

            UserStar DUMMY_USER_STAR5 = UserStar.builder()
                    .user(DUMMY_USER5)
                    .starUser(DUMMY_USER3)
                    .build();

            UserStar DUMMY_USER_STAR6 = UserStar.builder()
                    .user(DUMMY_USER6)
                    .starUser(DUMMY_USER7)
                    .build();

            UserStar DUMMY_USER_STAR7 = UserStar.builder()
                    .user(DUMMY_USER7)
                    .starUser(DUMMY_USER7)
                    .build();

            UserStar DUMMY_USER_STAR8 = UserStar.builder()
                    .user(DUMMY_USER8)
                    .starUser(DUMMY_USER4)
                    .build();

            UserStar DUMMY_USER_STAR9 = UserStar.builder()
                    .user(DUMMY_USER9)
                    .starUser(DUMMY_USER8)
                    .build();

            UserStar DUMMY_USER_STAR10 = UserStar.builder()
                    .user(DUMMY_USER10)
                    .starUser(DUMMY_USER7)
                    .build();

            userStarList.add(DUMMY_USER_STAR1);
            userStarList.add(DUMMY_USER_STAR2);
            userStarList.add(DUMMY_USER_STAR3);
            userStarList.add(DUMMY_USER_STAR4);
            userStarList.add(DUMMY_USER_STAR5);
            userStarList.add(DUMMY_USER_STAR6);
            userStarList.add(DUMMY_USER_STAR7);
            userStarList.add(DUMMY_USER_STAR8);
            userStarList.add(DUMMY_USER_STAR9);
            userStarList.add(DUMMY_USER_STAR10);


            userStarRepository.saveAll(userStarList);
            log.info("[UserStar] 더미 데이터 저장 완료");
        }
    }
}