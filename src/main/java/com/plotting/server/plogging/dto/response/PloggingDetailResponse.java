package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PloggingDetailResponse(
        String creator,
        String title,
        String content,
        Long currentPeople,
        Long maxPeople,
        PloggingType ploggingType,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        String endLocation
) {
    public static PloggingDetailResponse of(Plogging plogging, Long currentPeople) {
        return PloggingDetailResponse.builder()
                .creator(plogging.getUser().getNickname())
                .title(plogging.getTitle())
                .content(plogging.getContent())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .recruitStartDate(plogging.getRecruitStartDate())
                .recruitEndDate(plogging.getRecruitEndDate())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .endLocation(plogging.getEndLocation())
                .build();
    }
}
