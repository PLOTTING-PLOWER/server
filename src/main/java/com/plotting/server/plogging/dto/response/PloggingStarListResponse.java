package com.plotting.server.plogging.dto.response;

import com.plotting.server.plogging.domain.PloggingStar;
import lombok.Builder;

import java.util.List;

@Builder
public record PloggingStarListResponse(
        Long currentPeople,
        List<PloggingStarResponse> ploggingStarResponseList
) {
    public static PloggingStarListResponse from(Long currentPeople, List<PloggingStarResponse> ploggingStarResponseList) {
        return PloggingStarListResponse.builder()
                .currentPeople(currentPeople)
                .ploggingStarResponseList(ploggingStarResponseList)
                .build();
    }
}
