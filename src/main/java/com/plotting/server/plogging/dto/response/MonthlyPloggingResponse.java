package com.plotting.server.plogging.dto.response;

import lombok.Builder;

@Builder
public record MonthlyPloggingResponse(
        int year,
        int month,
        Long participationCount,
        Long totalHour
) {
    public static MonthlyPloggingResponse of(Long participationCount, Long totalHour) {
        return MonthlyPloggingResponse.builder()
                .participationCount(participationCount)
                .totalHour(totalHour)
                .build();
    }
}