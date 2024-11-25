package com.plotting.server.ranking.exception.errorCode;

import com.plotting.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RankingErrorCode implements ErrorCode {
    RANKING_NOT_FOUND(HttpStatus.NOT_FOUND, "Ranking not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
