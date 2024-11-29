package com.plotting.server.alarm.repository.init;

import com.plotting.server.alarm.domain.Alarm;
import com.plotting.server.alarm.repository.AlarmRepository;
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
public class AlarmInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (alarmRepository.count() > 0) {
            log.info("[UserStar]더미 데이터 존재");
        } else {
            User DUMMY_USER1 = userRepository.findById(1L).orElseThrow();
            User DUMMY_USER4 = userRepository.findById(4L).orElseThrow();
            User DUMMY_USER5 = userRepository.findById(5L).orElseThrow();
            User DUMMY_USER6 = userRepository.findById(6L).orElseThrow();


            List<Alarm> alarmList = new ArrayList<>();

            Alarm DUMMY_USER_STAR1 = Alarm.builder()
                    .user(DUMMY_USER1)
                    .content("유저1님이 플로깅에 참여 신청을 하셨습니다.")
                    .build();

            Alarm DUMMY_USER_STAR2 = Alarm.builder()
                    .user(DUMMY_USER1)
                    .content("유저2님이 플로깅에 참여 신청을 하셨습니다.")
                    .build();

            Alarm DUMMY_USER_STAR3 = Alarm.builder()
                    .user(DUMMY_USER1)
                    .content("유저2님이 플로깅에 참여 신청을 하셨습니다.")
                    .build();

            Alarm DUMMY_USER_STAR4 = Alarm.builder()
                    .user(DUMMY_USER4)
                    .content("유저2님이 플로깅에 참여 신청을 하셨습니다.")
                    .build();

            Alarm DUMMY_USER_STAR5 = Alarm.builder()
                    .user(DUMMY_USER5)
                    .content("유저2님이 플로깅에 참여 신청을 하셨습니다.")
                    .build();

            Alarm DUMMY_USER_STAR6 = Alarm.builder()
                    .user(DUMMY_USER6)
                    .content("유저2님이 플로깅에 참여 신청을 하셨습니다.")
                    .build();

            alarmList.add(DUMMY_USER_STAR1);
            alarmList.add(DUMMY_USER_STAR2);
            alarmList.add(DUMMY_USER_STAR3);
            alarmList.add(DUMMY_USER_STAR4);
            alarmList.add(DUMMY_USER_STAR5);
            alarmList.add(DUMMY_USER_STAR6);


            alarmRepository.saveAll(alarmList);
            log.info("[Alarm] 더미 데이터 저장 완료");
        }
    }
}