package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PloggingGetStarListResponse(
        List<PloggingGetStarResponse> ploggingGetStarResponseList
) {
    public static PloggingGetStarListResponse from(List<PloggingGetStarResponse> ploggingGetStarResponseList) {
        return PloggingGetStarListResponse.builder()
//                .currentPeople(currentPeople)
                .ploggingGetStarResponseList(ploggingGetStarResponseList)
                .build();
    }
}
