package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.response.PloggingGetStarResponse;
import com.plotting.server.plogging.dto.response.PloggingResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PloggingRepositoryCustom {

    List<PloggingResponse> findByFilters(String region, LocalDate startDate, LocalDate endDate, PloggingType type,
                                       Long spendTime, LocalDateTime startTime, Long maxPeople);

    List<PloggingGetStarResponse> findAllByTitleContaining(Long userId, String title, int size, Long lastSearchId);

//    boolean hasNext(String title, Long lastSearchId, int size);
}
