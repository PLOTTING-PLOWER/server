package com.plotting.server.plogging.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PloggingRequest(
        String title,
        String content,
        Long maxPeople,
        PloggingType type,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        String endLocation
) {
    public Plogging toPlogging(User user, BigDecimal startLatitude, BigDecimal startLongitude) {
        return Plogging.builder()
                .user(user)
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
                .startLatitude(startLatitude)
                .startLongitude(startLongitude)
                .build();
    }
}