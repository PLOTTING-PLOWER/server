package com.plotting.server.plogging.dto.response;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PloggingResponse(
        Long ploggingId,
        String title,
        Long maxPeople,
        PloggingType ploggingType,
        LocalDate recruitEndDate,
        LocalDateTime startTime,
        Long spendTime,
        String startLocation
) {

    public static PloggingResponse from(Plogging plogging) {
        return PloggingResponse.builder()
                .ploggingId(plogging.getId())
                .title(plogging.getTitle())
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .recruitEndDate(plogging.getRecruitEndDate())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .build();
    }
}
