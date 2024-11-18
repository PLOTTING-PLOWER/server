package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MyPloggingStarListResponse (
        List<PloggingResponse> responses
){
    public static MyPloggingStarListResponse from(List<PloggingResponse> responses) {
        return MyPloggingStarListResponse.builder()
                .responses(responses)
                .build();
    }
}

