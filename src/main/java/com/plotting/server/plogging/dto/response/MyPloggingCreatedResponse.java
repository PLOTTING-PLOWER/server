package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record MyPloggingCreatedResponse(
        Long ploggingId,
        PloggingType ploggingType,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startTime,
        Long spendTime,
        Integer currentPeople,
        Long maxPeople,
        String startLocation,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        Boolean isRecruiting,
        Boolean isStar
) {
    public static MyPloggingCreatedResponse of(Plogging plogging, Integer currentPeople, Boolean isRecruiting, Boolean isStar) {
        return MyPloggingCreatedResponse.builder()
                .ploggingId(plogging.getId())
                .ploggingType(plogging.getPloggingType())
                .title(plogging.getTitle())
                .content(plogging.getContent())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .startLocation(plogging.getStartLocation())
                .recruitStartDate(plogging.getRecruitStartDate())
                .recruitEndDate(plogging.getRecruitEndDate())
                .isRecruiting(isRecruiting)
                .isStar(isStar)
                .build();
    }
}