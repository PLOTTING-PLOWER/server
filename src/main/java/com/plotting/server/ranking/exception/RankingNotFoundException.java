package com.plotting.server.ranking.exception;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RankingNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
