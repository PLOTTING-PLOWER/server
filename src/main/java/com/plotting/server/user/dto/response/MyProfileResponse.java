package com.plotting.server.user.dto.response;

import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record MyProfileResponse(
        String nickname,
        String email,
        String profileMessage,
        String profileImageUrl,
        Boolean isProfilePublic
) {
    public static MyProfileResponse from(User user) {
        return MyProfileResponse.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileMessage(user.getProfileMessage())
                .profileImageUrl(user.getProfileImageUrl())
                .isProfilePublic(user.getIsProfilePublic())
                .build();
    }
}
