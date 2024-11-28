package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PloggingMapResponse(
        Long ploggingId,
        String title,
        Long currentPeople,
        Long maxPeople,
        PloggingType ploggingType,
        LocalDate recruitEndDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        BigDecimal startLatitude,
        BigDecimal startLongitude,
        Boolean isStar
) {

    public static PloggingMapResponse of(Plogging plogging, Long currentPeople, Boolean isStar) {
        return PloggingMapResponse.builder()
                .ploggingId(plogging.getId())
                .title(plogging.getTitle())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .recruitEndDate(plogging.getRecruitEndDate())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .startLatitude(plogging.getStartLatitude())
                .startLongitude(plogging.getStartLongitude())
                .isStar(isStar)
                .build();
    }
}
