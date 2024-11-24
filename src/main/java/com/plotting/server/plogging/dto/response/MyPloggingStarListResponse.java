package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MyPloggingStarListResponse (
        List<PloggingStarResponse> responses
){
    public static MyPloggingStarListResponse from(List<PloggingStarResponse> responses) {
        return MyPloggingStarListResponse.builder()
                .responses(responses)
                .build();
    }
}

