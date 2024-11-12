package com.plotting.server.cardnews.exception;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardNewsNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}