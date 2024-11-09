package com.plotting.server.plogging.dto.response;

import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record HomeResponse(
        PloggingListResponse ploggingResponseList,
        PlowerListResponse plowerResponseList,
        String userNickname
) {
    public static HomeResponse of(PloggingListResponse ploggingResponseList,
                                  PlowerListResponse plowerResponseList,
                                  User user) {
        return HomeResponse.builder()
                .ploggingResponseList(ploggingResponseList)
                .plowerResponseList(plowerResponseList)
                .userNickname(user.getNickname())
                .build();
    }
}
