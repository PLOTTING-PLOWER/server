package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyPloggingCreatedResponse(
        Long ploggingId,
        PloggingType ploggingType,
        String title,
        String startLocation,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startTime,
        Long spendTime,
        Long currentPeople,
        Long maxPeople,
        Boolean isRecruiting
) {
    public static MyPloggingCreatedResponse of(Plogging plogging, Long currentPeople, Boolean isRecruiting) {
        return MyPloggingCreatedResponse.builder()
                .ploggingId(plogging.getId())
                .ploggingType(plogging.getPloggingType())
                .title(plogging.getTitle())
                .startLocation(plogging.getStartLocation())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .isRecruiting(isRecruiting)
                .build();
    }
}