package com.plotting.server.user.dto.response;


import com.plotting.server.user.domain.UserStar;
import lombok.Builder;

@Builder
public record MyUserStarResponse(
        Long userId,
        String profileImageUrl,
        String nickname,
        String profileMessage,
        Boolean isProfilePublic

) {
    public static MyUserStarResponse from(UserStar userStar) {
        return MyUserStarResponse.builder()
                .userId(userStar.getStarUser().getId())
                .profileImageUrl(userStar.getStarUser().getProfileImageUrl())
                .nickname(userStar.getStarUser().getNickname())
                .profileMessage(userStar.getStarUser().getProfileMessage())
                .isProfilePublic(userStar.getStarUser().getIsProfilePublic())
                .build();
    }
}
