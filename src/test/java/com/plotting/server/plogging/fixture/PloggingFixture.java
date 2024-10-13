package com.plotting.server.plogging.fixture;

import com.plotting.server.plogging.domain.Plogging;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.plotting.server.plogging.domain.type.PloggingType.DIRECT;

public class PloggingFixture {
    public static final Plogging PLOGGING = Plogging.builder()
            .title("플로깅")
            .content("플로깅 같이 할 사람?")
            .maxPeople(5L)
            .ploggingType(DIRECT)
            .recruitStartDate(LocalDate.now())
            .recruitEndDate(LocalDate.now())
            .startTime(LocalDate.now().atStartOfDay())
            .spendTime(60L)
            .startLocation("서울시 중구")
            .startLatitude(BigDecimal.valueOf(37.569477583193))
            .startLongitude(BigDecimal.valueOf(126.97779641524))
            .build();

    static {
        ReflectionTestUtils.setField(PLOGGING, "id", 1L);
    }
}