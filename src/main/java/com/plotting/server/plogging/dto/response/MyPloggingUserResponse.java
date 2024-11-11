package com.plotting.server.plogging.dto.response;

import com.plotting.server.plogging.domain.PloggingUser;
import lombok.Builder;

@Builder
public record MyPloggingUserResponse(
        Long ploggingUserId,
        String nickname,
        String profileImageUrl
) {
    public static MyPloggingUserResponse from(PloggingUser ploggingUser) {
        return MyPloggingUserResponse.builder()
                .ploggingUserId(ploggingUser.getId())
                .nickname(ploggingUser.getUser().getNickname())
                .profileImageUrl(ploggingUser.getUser().getProfileImageUrl())
                .build();
    }
}