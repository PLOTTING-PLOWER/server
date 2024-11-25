package com.plotting.server.plogging.dto.response;

import com.plotting.server.plogging.domain.PloggingUser;
import lombok.Builder;

@Builder
public record PloggingUserResponse(
        String profileImageUrl,
        String nickname,
        String profileMessage
) {
    public static PloggingUserResponse from(PloggingUser ploggingUser) {
        return PloggingUserResponse.builder()
                .profileImageUrl(ploggingUser.getUser().getProfileImageUrl())
                .nickname(ploggingUser.getUser().getNickname())
                .profileMessage(ploggingUser.getUser().getProfileMessage())
                .build();
    }
}