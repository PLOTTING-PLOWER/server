package com.plotting.server.plogging.repository.init;

import com.plotting.server.global.util.DummyDataInit;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@DummyDataInit
public class PloggingUserInitializer implements ApplicationRunner {

    private final PloggingUserRepository ploggingUserRepository;
    private final UserRepository userRepository;
    private final PloggingRepository ploggingRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (ploggingUserRepository.count() > 0) {
            log.info("[PloggingUser]더미 데이터 존재");
        } else {
            User DUMMY_USER1 = userRepository.findById(1L).orElseThrow();
            User DUMMY_USER2 = userRepository.findById(2L).orElseThrow();
            User DUMMY_USER3 = userRepository.findById(3L).orElseThrow();
            User DUMMY_USER4 = userRepository.findById(4L).orElseThrow();
            User DUMMY_USER5 = userRepository.findById(5L).orElseThrow();

            Plogging DUMMY_PLOGGING1 = ploggingRepository.findById(1L).orElseThrow();
            Plogging DUMMY_PLOGGING2 = ploggingRepository.findById(2L).orElseThrow();
            Plogging DUMMY_PLOGGING3 = ploggingRepository.findById(3L).orElseThrow();

            List<PloggingUser> ploggingUserList = new ArrayList<>();

            PloggingUser DUMMY_PLOGGING_USER1 = PloggingUser.builder()
                    .user(DUMMY_USER1)
                    .plogging(DUMMY_PLOGGING1)
                    .isAssigned(true)
                    .build();

            PloggingUser DUMMY_PLOGGING_USER2 = PloggingUser.builder()
                    .user(DUMMY_USER2)
                    .plogging(DUMMY_PLOGGING1)
                    .isAssigned(true)
                    .build();

            PloggingUser DUMMY_PLOGGING_USER3 = PloggingUser.builder()
                    .user(DUMMY_USER3)
                    .plogging(DUMMY_PLOGGING2)
                    .isAssigned(true)
                    .build();

            PloggingUser DUMMY_PLOGGING_USER4 = PloggingUser.builder()
                    .user(DUMMY_USER4)
                    .plogging(DUMMY_PLOGGING2)
                    .isAssigned(true)
                    .build();

            PloggingUser DUMMY_PLOGGING_USER5 = PloggingUser.builder()
                    .user(DUMMY_USER5)
                    .plogging(DUMMY_PLOGGING2)
                    .isAssigned(false)
                    .build();

            PloggingUser DUMMY_PLOGGING_USER6 = PloggingUser.builder()
                    .user(DUMMY_USER1)
                    .plogging(DUMMY_PLOGGING2)
                    .isAssigned(true)
                    .build();

            PloggingUser DUMMY_PLOGGING_USER7 = PloggingUser.builder()
                    .user(DUMMY_USER1)
                    .plogging(DUMMY_PLOGGING3)
                    .isAssigned(true)
                    .build();

            ploggingUserList.add(DUMMY_PLOGGING_USER1);
            ploggingUserList.add(DUMMY_PLOGGING_USER2);
            ploggingUserList.add(DUMMY_PLOGGING_USER3);
            ploggingUserList.add(DUMMY_PLOGGING_USER4);
            ploggingUserList.add(DUMMY_PLOGGING_USER5);
            ploggingUserList.add(DUMMY_PLOGGING_USER6);
            ploggingUserList.add(DUMMY_PLOGGING_USER7);

            ploggingUserRepository.saveAll(ploggingUserList);
        }
    }
}