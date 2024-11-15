package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PloggingMapResponse(
        Long ploggingId,
        String title,
        Long currentPeople,
        Long maxPeople,
        PloggingType ploggingType,
        LocalDate recruitEndDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,
        Long spendTime,
        String startLocation,
        BigDecimal startLatitude,
        BigDecimal startLongitude,
        BigDecimal averageLatitude,   // 클러스터 평균 위도
        BigDecimal averageLongitude,  // 클러스터 평균 경도
        Integer clusterCount          // 클러스터 내 모임 개수
) {

    public static PloggingMapResponse forbasic(Plogging plogging, Long currentPeople) {
        return PloggingMapResponse.builder()
                .ploggingId(plogging.getId())
                .title(plogging.getTitle())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .recruitEndDate(plogging.getRecruitEndDate())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .startLatitude(plogging.getStartLatitude())
                .startLongitude(plogging.getStartLongitude())
                .build();
    }

    public static PloggingMapResponse forCluster(Plogging plogging, Long currentPeople,BigDecimal averageLatitude, BigDecimal averageLongitude, int count) {
        return PloggingMapResponse.builder()
                .ploggingId(plogging.getId())
                .title(plogging.getTitle())
                .currentPeople(currentPeople)
                .maxPeople(plogging.getMaxPeople())
                .ploggingType(plogging.getPloggingType())
                .recruitEndDate(plogging.getRecruitEndDate())
                .startTime(plogging.getStartTime())
                .spendTime(plogging.getSpendTime())
                .startLocation(plogging.getStartLocation())
                .startLatitude(plogging.getStartLatitude())
                .startLongitude(plogging.getStartLongitude())
                .averageLatitude(averageLatitude)   // 평균 위도
                .averageLongitude(averageLongitude) // 평균 경도
                .clusterCount(count)                // 클러스터 내 모임 개수
                .build();
    }
}
