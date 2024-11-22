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
    NAME("님이 "),
    DIRECT_PARTICIPANT("에 참가하였습니다."),
    DIRECT_COMPLETE_PARTICIPANT("에 참가 승인되었습니다."),
    ASSIGN_PARTICIPANT("에 참가 요청을 하였습니다."),
    ASSIGN_COMPLETE_PARTICIPANT("에 참가 요청이 완료되었습니다."),
    PLOGGING_UPDATE("의 정보를 수정하였습니다."),
    PLOGGING_DELETE("을 삭제하였습니다."),
    ;

    private final String message;
}