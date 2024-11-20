package com.plotting.server.user.dto.response;

import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record DetailProfileResponse(
        String nickname,
        String profileMessage,
        String profileImageUrl,
        Boolean IsStar,         // 내가 즐겨찾기 했는지 여부
        Long totalPloggingNumber,   // 총 플로깅 획수
        Long totalPloggingTime,     // 총 플로깅 시간
        Long ranking                // 랭킹

        // 활동 정보는 미정,,

) {
    public static DetailProfileResponse of(User user, Boolean isStar, Ranking ranking, Long totalPloggingNumber, Long totalPloggingTime) {
        return DetailProfileResponse.builder()
                .nickname(user.getNickname())
                .profileMessage(user.getProfileMessage())
                .profileImageUrl(user.getProfileImageUrl())
                .IsStar(isStar)
                .totalPloggingNumber(totalPloggingNumber)
                .totalPloggingTime(totalPloggingTime)
                .ranking(ranking.getHourRank())
                .build();
    }
}
