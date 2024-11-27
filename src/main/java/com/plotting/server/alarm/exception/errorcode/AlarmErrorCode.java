package com.plotting.server.alarm.exception.errorcode;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AlarmErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    FCM_FAILED(HttpStatus.EXPECTATION_FAILED, "Fcm failed"),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
