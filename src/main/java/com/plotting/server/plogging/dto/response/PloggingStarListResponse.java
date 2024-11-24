package com.plotting.server.plogging.dto.response;

import java.util.List;

public record PloggingStarListResponse(
        Long currentPeople,
        List<PloggingStarResponse> ploggingStarResponseList
) {
    public static PloggingStarListResponse from(Long currentPeople, List<PloggingStarResponse> ploggingStarResponseList) {
        return new PloggingStarListResponse(currentPeople, ploggingStarResponseList);
    }
}
