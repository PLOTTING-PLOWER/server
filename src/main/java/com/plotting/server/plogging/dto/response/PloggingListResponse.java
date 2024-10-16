package com.plotting.server.plogging.dto.response;

import java.util.List;

public record PloggingListResponse(
        List<PloggingResponse> ploggingResponseList
) {
}
