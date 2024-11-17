package com.plotting.server.user.dto.response;


import com.plotting.server.user.domain.UserStar;
import lombok.Builder;

@Builder
public record MyUserStarResponse(
        Long userId,
        String profileImageUrl,
        String nickname,
        String profileMessage
) {
    public static MyUserStarResponse from(UserStar userStar) {
        return MyUserStarResponse.builder()
                .userId(userStar.getUser().getId())
                .profileImageUrl(userStar.getUser().getProfileImageUrl())
                .nickname(userStar.getUser().getNickname())
                .profileMessage(userStar.getUser().getProfileMessage())
                .build();
    }
}
