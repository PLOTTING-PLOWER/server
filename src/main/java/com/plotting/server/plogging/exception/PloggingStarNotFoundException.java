package com.plotting.server.plogging.exception;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PloggingStarNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}