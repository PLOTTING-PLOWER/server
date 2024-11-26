package com.plotting.server.ranking.dto.response;

import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record RankingResponse (
        Long userId,
     String nickname,
     String profileImageUrl,
     Long hourRank,
     Long totalHours,
     Long totalCount
){
    public static RankingResponse from(User user) {
        return RankingResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .hourRank(0L)
                .totalHours(0L)
                .totalCount(0L)
                .build();
    }
}
