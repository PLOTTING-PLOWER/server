package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MyPloggingCreatedListResponse(
        List<MyPloggingCreatedResponse> myPloggings
) {
    public MyPloggingCreatedListResponse from(List<MyPloggingCreatedResponse> myPloggings) {
        return MyPloggingCreatedListResponse.builder()
                .myPloggings(myPloggings)
                .build();
    }
}