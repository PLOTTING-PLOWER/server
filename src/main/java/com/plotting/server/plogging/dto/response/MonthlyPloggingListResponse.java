package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MonthlyPloggingListResponse(
        List<MonthlyPloggingResponse> responses
) {
    public static MonthlyPloggingListResponse from(List<MonthlyPloggingResponse> responses) {
        return MonthlyPloggingListResponse.builder()
                .responses(responses)
                .build();
    }
}