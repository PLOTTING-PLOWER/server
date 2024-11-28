package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyPloggingScheduledResponse(

        Long ploggingId,
        PloggingType ploggingType,
        String title,
        Long currentPeople,
        Long maxPeople,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        Boolean isAssigned,
        Boolean isStar

) {

    public static MyPloggingScheduledResponse of(Plogging plogging, Long currentPeople, Boolean isAssigned, Boolean isStar) {
        return MyPloggingScheduledResponse.builder()
                .ploggingId(plogging.getId())
                .title(plogging.getTitle())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .isAssigned(isAssigned)
                .isStar(isStar)
                .build();
    }
}
