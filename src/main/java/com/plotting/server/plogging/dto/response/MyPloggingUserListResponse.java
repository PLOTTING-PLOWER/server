package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MyPloggingUserListResponse(
        List<MyPloggingUserResponse> responses
) {
    public static MyPloggingUserListResponse from(List<MyPloggingUserResponse> responses) {
        return MyPloggingUserListResponse.builder()
                .responses(responses)
                .build();
    }
}