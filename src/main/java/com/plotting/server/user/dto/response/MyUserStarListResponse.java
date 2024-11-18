package com.plotting.server.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MyUserStarListResponse(
        List<MyUserStarResponse> responses
) {
    public static MyUserStarListResponse from(List<MyUserStarResponse> responses) {
        return MyUserStarListResponse.builder()
                .responses(responses)
                .build();
    }
}