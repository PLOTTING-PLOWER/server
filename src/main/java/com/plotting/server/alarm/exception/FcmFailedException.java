package com.plotting.server.alarm.exception;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FcmFailedException extends RuntimeException {
        private final ErrorCode errorCode;
}
