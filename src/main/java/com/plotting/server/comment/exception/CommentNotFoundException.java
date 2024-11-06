package com.plotting.server.comment.exception;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
