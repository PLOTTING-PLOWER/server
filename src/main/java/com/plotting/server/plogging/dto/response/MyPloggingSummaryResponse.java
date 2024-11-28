package com.plotting.server.plogging.dto.response;

import lombok.Builder;

@Builder
public record MyPloggingSummaryResponse (
     int totalPloggingCount,   // 총 플로깅 횟수
     long totalSpendTime,      // 총 플로깅 시간 (시)
     String nickname,          // 유저 닉네임
     String profileImageUrl    // 유저 프로필 이미지 URL

) {
    public static MyPloggingSummaryResponse of(int totalPloggingCount, long totalSpendTime, String nickname, String profileImageUrl){

        return MyPloggingSummaryResponse.builder()
                .totalPloggingCount(totalPloggingCount)
                .totalSpendTime(totalSpendTime)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

}





