package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PloggingListResponse(
        Long currentPeople,
        List<PloggingResponse> ploggingResponseList
) {

    public static PloggingListResponse from(Long currentPeople, List<PloggingResponse> ploggingResponseList) {
        return PloggingListResponse.builder()
                .currentPeople(currentPeople)
                .ploggingResponseList(ploggingResponseList)
                .build();
    }
}
