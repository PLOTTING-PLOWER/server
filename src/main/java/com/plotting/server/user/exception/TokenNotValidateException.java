package com.plotting.server.user.exception;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenNotValidateException extends RuntimeException {
    private final ErrorCode errorCode;
}
