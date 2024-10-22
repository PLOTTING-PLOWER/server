package com.plotting.server.plogging.dto.request;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PloggingRequest(
        PloggingType type,
        Long maxPeople,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        String title,
        String content,
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        String endLocation
) {
    public Plogging toEntity(Plogging plogging) {
        return Plogging .builder()
                .title(title)
                .content(content)
                .maxPeople(maxPeople)
                .ploggingType(type)
                .recruitStartDate(recruitStartDate)
                .startTime(startTime)
                .recruitEndDate(recruitEndDate)
                .spendTime(spendTime)
                .startLocation(startLocation)
                .endLocation(endLocation)
                .build();
    }
}
