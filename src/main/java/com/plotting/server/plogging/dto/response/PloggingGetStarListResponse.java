package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PloggingGetStarListResponse(
        Boolean hasNext,
        List<PloggingGetStarResponse> ploggingGetStarResponseList
) {
    public static PloggingGetStarListResponse from(Boolean hasNext, List<PloggingGetStarResponse> ploggingGetStarResponseList) {
        return PloggingGetStarListResponse.builder()
                .hasNext(hasNext)
                .ploggingGetStarResponseList(ploggingGetStarResponseList)
                .build();
    }
}
