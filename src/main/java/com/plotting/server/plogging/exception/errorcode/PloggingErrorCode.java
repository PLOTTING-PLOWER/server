package com.plotting.server.plogging.exception.errorcode;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PloggingErrorCode implements ErrorCode {
    PLOGGING_NOT_FOUND(HttpStatus.NOT_FOUND, "Plogging not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}