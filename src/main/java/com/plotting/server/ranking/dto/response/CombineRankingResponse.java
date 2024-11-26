package com.plotting.server.ranking.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CombineRankingResponse(
        List<RankingResponse> topRankings,
        RankingResponse myRanking
) {
    public static CombineRankingResponse of(List<RankingResponse> topRankings, RankingResponse myRanking) {
        return CombineRankingResponse.builder()
                .topRankings(topRankings)
                .myRanking(myRanking)
                .build();
    }
}
