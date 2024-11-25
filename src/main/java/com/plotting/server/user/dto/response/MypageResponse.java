package com.plotting.server.user.dto.response;

import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record MypageResponse(
        String nickname,
        String profileImageUrl,
        Boolean isAlarmAllowed
) {
    public static MypageResponse from(User user){
        return MypageResponse.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .isAlarmAllowed(user.getIsAlarmAllowed())
                .build();
    }
}
