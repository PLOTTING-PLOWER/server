package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyPloggingParticipatedResponse(

        Long ploggingId,
        PloggingType ploggingType,
        String title,
        Integer currentPeople,
        Long maxPeople,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        Boolean isStar

) {

    public static MyPloggingParticipatedResponse of(Plogging plogging, Integer currentPeople, Boolean isStar) {
        return MyPloggingParticipatedResponse.builder()
                .ploggingId(plogging.getId())
                .title(plogging.getTitle())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .isStar(isStar)
                .build();
    }
}
