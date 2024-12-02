package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PloggingStarListResponse(
//        Long currentPeople,
        List<PloggingStarResponse> ploggingStarResponseList
) {
    public static PloggingStarListResponse from(List<PloggingStarResponse> ploggingStarResponseList) {
        return PloggingStarListResponse.builder()
//                .currentPeople(currentPeople)
                .ploggingStarResponseList(ploggingStarResponseList)
                .build();
    }
}
