package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PloggingTimeResponse (
        Long spendTime,
        LocalDateTime startTime
){

}
