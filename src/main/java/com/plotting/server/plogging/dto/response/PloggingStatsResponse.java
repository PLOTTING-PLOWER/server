package com.plotting.server.plogging.dto.response;

import lombok.Builder;

@Builder
public record PloggingStatsResponse(
        Long totalPloggingNumber,
        Long totalPloggingTime
) {
    public static PloggingStatsResponse of(Long totalPloggingNumber, Long totalPloggingTime) {
        return PloggingStatsResponse.builder()
                .totalPloggingNumber(totalPloggingNumber)
                .totalPloggingTime(totalPloggingTime)
                .build();
    }
}
