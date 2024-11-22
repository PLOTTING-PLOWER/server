package com.plotting.server.ranking.dto.response;

import lombok.Builder;

@Builder
public record RankingResponse (
        Long userId,
     String nickname,
     String profileImageUrl,
     Long hourRank,
     Long totalHours,
     Long totalCount
){}
