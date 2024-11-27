package com.plotting.server.alarm.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record AlarmListResponse(
        List<AlarmResponse> alarmList
){
    public static AlarmListResponse from(List<AlarmResponse> alarmList){
        return AlarmListResponse.builder()
                .alarmList(alarmList)
                .build();
    }
}
