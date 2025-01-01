package com.plotting.server.plogging.dto.response;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PloggingGetStarResponse(
        Long ploggingId,
        String title,
        Integer currentPeople,
        Long maxPeople,
        PloggingType ploggingType,
        LocalDate recruitEndDate,
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        Boolean isStar
) {
    public static PloggingGetStarResponse of(Plogging plogging, int currentPeople, Boolean isStar) {
        return PloggingGetStarResponse.builder()
                .ploggingId(plogging.getId())
                .title(plogging.getTitle())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .recruitEndDate(plogging.getRecruitEndDate())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .isStar(isStar)
                .build();
    }
}