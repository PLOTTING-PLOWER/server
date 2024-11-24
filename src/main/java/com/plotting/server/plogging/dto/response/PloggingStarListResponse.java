package com.plotting.server.plogging.dto.response;

import java.util.List;

public record PloggingStarListResponse(
        List<PloggingStarResponse> ploggingStarResponseList
) {
    public static PloggingStarListResponse from(List<PloggingStarResponse> ploggingStarResponseList) {
        return new PloggingStarListResponse(ploggingStarResponseList);
    }
}
