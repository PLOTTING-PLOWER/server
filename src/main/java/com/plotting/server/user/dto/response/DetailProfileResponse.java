package com.plotting.server.user.dto.response;

import com.plotting.server.plogging.dto.response.PloggingStatsResponse;
import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record DetailProfileResponse(
        String nickname,
        String profileMessage,
        String profileImageUrl,
        Boolean isStar,         // 내가 즐겨찾기 했는지 여부
        Long totalPloggingNumber,   // 총 플로깅 획수
        Long totalPloggingTime,     // 총 플로깅 시간
        Long ranking                // 랭킹

        // 활동 정보는 미정,,

) {
    public static DetailProfileResponse of(User user, Boolean isStar, Ranking ranking, PloggingStatsResponse ploggingStatsResponse) {
        return DetailProfileResponse.builder()
                .nickname(user.getNickname())
                .profileMessage(user.getProfileMessage())
                .profileImageUrl(user.getProfileImageUrl())
                .isStar(isStar)
                .totalPloggingNumber(ploggingStatsResponse != null ? ploggingStatsResponse.totalPloggingNumber() : 0) // 기본값 처리
                .totalPloggingTime(ploggingStatsResponse != null ? ploggingStatsResponse.totalPloggingTime() : 0L) // 기본값 처리
                .ranking(ranking != null ? ranking.getHourRank() : null) // 랭킹이 없으면 null
                .build();
    }
}
