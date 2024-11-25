package com.plotting.server.plogging.dto.response;

import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record HomeResponse(
        PloggingListResponse ploggingStarResponseList,
        PlowerListResponse plowerResponseList,
        String userNickname
) {
    public static HomeResponse of(PloggingListResponse ploggingListResponse,
                                  PlowerListResponse plowerResponseList,
                                  User user) {
        return HomeResponse.builder()
                .ploggingStarResponseList(ploggingListResponse)
                .plowerResponseList(plowerResponseList)
                .userNickname(user.getNickname())
                .build();
    }
}
