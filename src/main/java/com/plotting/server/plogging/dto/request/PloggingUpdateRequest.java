package com.plotting.server.plogging.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PloggingUpdateRequest(
        String title,
        String content,
        Long maxPeople,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startTime,
        Long spendTime
) {
}