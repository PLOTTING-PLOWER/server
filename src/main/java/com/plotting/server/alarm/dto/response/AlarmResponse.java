package com.plotting.server.alarm.dto.response;

import com.plotting.server.alarm.domain.Alarm;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AlarmResponse (
        String content,
        LocalDateTime createdDate
){
    public static AlarmResponse from(Alarm alarm) {
        return AlarmResponse.builder()
                .content(alarm.getContent())
                .createdDate(alarm.getCreatedDate())
                .build();
    }
}
