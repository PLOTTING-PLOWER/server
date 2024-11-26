package com.plotting.server.ranking.dto.response;

import lombok.Builder;

@Builder
public record UpdateRankingResponse(
        Long userId,
        Long totalHours,
        Long totalCount
){
    public static UpdateRankingResponse from(UpdateRankingResponse response) {
        return UpdateRankingResponse.builder()
                .userId(response.userId)
                .totalHours(response.totalHours)
                .totalCount(response.totalCount)
                .build();
    }
}
