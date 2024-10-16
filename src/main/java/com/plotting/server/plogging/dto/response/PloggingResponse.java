package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PloggingResponse(
        String title,
        Long currentPeople,
        Long maxPeople,
        PloggingType ploggingType,
        LocalDate recruitEndDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,
        Long spendTime,
        String startLocation
) {
    @Builder
   public static PloggingResponse of(String title, Long currentPeople, Long maxPeople, PloggingType ploggingType,
                                     LocalDate recruitEndDate, LocalDateTime startTime, Long spendTime, String startLocation) {
       return PloggingResponse.builder()
               .title(title)
               .currentPeople(currentPeople)
               .maxPeople(maxPeople)
               .ploggingType(ploggingType)
               .recruitEndDate(recruitEndDate)
               .startTime(startTime)
               .spendTime(spendTime)
               .startLocation(startLocation)
               .build();
   }
}
