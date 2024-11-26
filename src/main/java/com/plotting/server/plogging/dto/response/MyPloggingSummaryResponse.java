package com.plotting.server.plogging.dto.response;

import lombok.Builder;

@Builder
public record MyPloggingSummaryResponse (
     int totalPloggingCount,   // 총 플로깅 횟수
     long totalSpendTime,      // 총 플로깅 시간 (시)
     String nickname,          // 유저 닉네임
     String profileImageUrl    // 유저 프로필 이미지 URL

) {

}





