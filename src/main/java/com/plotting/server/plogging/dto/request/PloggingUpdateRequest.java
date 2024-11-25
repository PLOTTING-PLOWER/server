package com.plotting.server.plogging.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PloggingUpdateRequest(
        String title,
        String content,
        Long maxPeople,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        LocalDateTime startTime,
        Long spendTime
) {
}