package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PloggingListResponse(
        List<PloggingResponse> ploggingResponseList
) {

    public static PloggingListResponse from(List<PloggingResponse> ploggingResponseList) {
        return PloggingListResponse.builder()
                .ploggingResponseList(ploggingResponseList)
                .build();
    }
}
