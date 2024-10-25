package com.plotting.server.plogging.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PloggingConstants {
    ALREADY_COMPLETE("참가중이거나 참가 신청을 완료한 플로깅입니다."),
    ASSIGN_COMPLETE("참가 요청이 완료되었습니다."),
    FULL_RECRUIT("플로깅 모집 인원이 마감되었습니다."),
    DIRECT_COMPLETE("참가 승인되었습니다."),
    ;

    private final String message;
}