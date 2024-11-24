package com.plotting.server.plogging.dto.response;

import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record HomeResponse(
        PloggingStarListResponse ploggingStarResponseList,
        PlowerListResponse plowerResponseList,
        String userNickname
) {
    public static HomeResponse of(PloggingStarListResponse ploggingStarListResponse,
                                  PlowerListResponse plowerResponseList,
                                  User user) {
        return HomeResponse.builder()
                .ploggingStarResponseList(ploggingStarListResponse)
                .plowerResponseList(plowerResponseList)
                .userNickname(user.getNickname())
                .build();
    }
}
