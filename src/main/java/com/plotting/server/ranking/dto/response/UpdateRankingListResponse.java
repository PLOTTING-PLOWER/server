package com.plotting.server.ranking.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UpdateRankingListResponse(
        List<UpdateRankingResponse> rankingListResponse
) {
    public static UpdateRankingListResponse from(List<UpdateRankingResponse> response) {
        return UpdateRankingListResponse.builder()
                .rankingListResponse(response)
                .build();
    }
}
