package com.plotting.server.plogging.repository.init;

import com.plotting.server.global.util.DummyDataInit;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.repository.PloggingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.plotting.server.plogging.domain.type.PloggingType.ASSIGN;
import static com.plotting.server.plogging.domain.type.PloggingType.DIRECT;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class PloggingInitializer implements ApplicationRunner {

    private final PloggingRepository ploggingRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (ploggingRepository.count() > 0) {
            log.info("[Plogging]더미 데이터 존재");
        } else {
            List<Plogging> ploggingList = new ArrayList<>();

            Plogging DUMMY_PLOGGING1 = Plogging.builder()
                    .title("한강 플로깅")
                    .content("한강에서 같이 뛰면서 플로깅 하실 분들 구합니다!")
                    .maxPeople(5L)
                    .ploggingType(DIRECT)
                    .recruitStartDate(LocalDate.parse("2024-10-12"))
                    .recruitEndDate(LocalDate.parse("2024-11-30"))
                    .startTime(LocalDateTime.of(2024, 12, 1, 14, 0, 0))
                    .spendTime(120L)
                    .startLocation("서울 영등포구 여의동로 330 한강사업본부 여의도안내센터")
                    .startLatitude(BigDecimal.valueOf(37.5263886632))
                    .startLongitude(BigDecimal.valueOf(126.933612357))
                    .endLocation("서울특별시 영등포구 여의동로 330")
                    .build();

            Plogging DUMMY_PLOGGING2 = Plogging.builder()
                    .title("올림픽 공원 플로깅")
                    .content("올림픽 공원에서 같이 플로깅 어떄요?")
                    .maxPeople(8L)
                    .ploggingType(ASSIGN)
                    .recruitStartDate(LocalDate.parse("2024-09-15"))
                    .recruitEndDate(LocalDate.parse("2024-11-20"))
                    .startTime(LocalDateTime.of(2024, 11, 21, 12, 0, 0))
                    .spendTime(60L)
                    .startLocation("서울특별시 송파구 올림픽로 424")
                    .startLatitude(BigDecimal.valueOf(37.5206868))
                    .startLongitude(BigDecimal.valueOf(127.1171114))
                    .endLocation("")
                    .build();

            ploggingList.add(DUMMY_PLOGGING1);
            ploggingList.add(DUMMY_PLOGGING2);

            ploggingRepository.saveAll(ploggingList);
        }
    }
}