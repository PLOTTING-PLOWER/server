package com.plotting.server.plogging.dto.response;

import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record HomeResponse(
        PloggingGetStarListResponse ploggingGetStarResponseList,
        PlowerListResponse plowerResponseList,
        String userNickname,
        String userImageUrl
) {
    public static HomeResponse of(PloggingGetStarListResponse ploggingGetStarListResponse,
                                  PlowerListResponse plowerResponseList,
                                  User user) {
        return HomeResponse.builder()
                .ploggingGetStarResponseList(ploggingGetStarListResponse)
                .plowerResponseList(plowerResponseList)
                .userNickname(user.getNickname())
                .userImageUrl(user.getProfileImageUrl())
                .build();
    }
}
