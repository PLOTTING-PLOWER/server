package com.plotting.server.plogging.repository.init;

import com.plotting.server.global.util.DummyDataInit;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingStar;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingStarRepository;
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
public class PloggingStarInitializer implements ApplicationRunner {

    private final PloggingStarRepository ploggingStarRepository;
    private final PloggingRepository ploggingRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (ploggingStarRepository.count() > 0) {
            log.info("[PloggingStar] 더미 데이터 존재");
        } else {
            // User 객체 가져오기
            User DUMMY_USER1 = userRepository.findById(1L).orElseThrow();
            User DUMMY_USER2 = userRepository.findById(2L).orElseThrow();

            // Plogging 객체 가져오기
            Plogging DUMMY_PLOGGING1 = ploggingRepository.findById(1L).orElseThrow();
            Plogging DUMMY_PLOGGING2 = ploggingRepository.findById(2L).orElseThrow();

            List<PloggingStar> ploggingStarList = new ArrayList<>();

            // PloggingStar 객체 생성
            PloggingStar DUMMY_PLOGGING_STAR1 = PloggingStar.builder()
                    .user(DUMMY_USER1)
                    .plogging(DUMMY_PLOGGING1)
                    .build();

            PloggingStar DUMMY_PLOGGING_STAR2 = PloggingStar.builder()
                    .user(DUMMY_USER2)
                    .plogging(DUMMY_PLOGGING2)
                    .build();

            // 리스트에 추가
            ploggingStarList.add(DUMMY_PLOGGING_STAR1);
            ploggingStarList.add(DUMMY_PLOGGING_STAR2);

            // 저장
            ploggingStarRepository.saveAll(ploggingStarList);
            log.info("[PloggingStar] 더미 데이터 저장 완료");
        }
    }
}

