package com.plotting.server.user.dto.request;

import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record MyProfileUpdateRequest(
        String nickname,
        String profileMessage,
        String profileImageUrl,
        Boolean isProfilePublic
) {
    public static MyProfileUpdateRequest from(User user) {
        return MyProfileUpdateRequest.builder()
                .nickname(user.getNickname())
                .profileMessage(user.getProfileMessage())
                .profileImageUrl(user.getProfileImageUrl())
                .isProfilePublic(user.getIsProfilePublic())
                .build();
    }
}
