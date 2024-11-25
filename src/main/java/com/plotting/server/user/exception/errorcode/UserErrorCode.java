package com.plotting.server.user.exception.errorcode;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_STAR_NOT_FOUND(HttpStatus.NOT_FOUND, "UserStar not found"),
    USER_ALREADY_EXISTS(HttpStatus.ALREADY_REPORTED, "User already exists"),
    TOKEN_NOT_VALIDATE(HttpStatus.UNAUTHORIZED, "token not validate"),
    ProfileNotPublic(HttpStatus.FORBIDDEN, "Profile not public"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
