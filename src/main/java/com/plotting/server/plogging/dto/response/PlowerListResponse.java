package com.plotting.server.plogging.dto.response;

import java.util.List;

public record PlowerListResponse(
        List<PlowerResponse> plowerResponseList
) {
    public static PlowerListResponse from(List<PlowerResponse> plowerResponseList) {
        return new PlowerListResponse(plowerResponseList);
    }
}
