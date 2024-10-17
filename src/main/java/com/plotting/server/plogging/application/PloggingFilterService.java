package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.plotting.server.plogging.repository.PloggingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PloggingFilterService {

    private final PloggingRepository ploggingRepository;

    public List<PloggingResponse> findListByFilter(List<String> region, LocalDate startDate, LocalDate endDate, PloggingType type,
                                                 Long spendTime, LocalDateTime startTime, Long maxPeople) {

        return ploggingRepository.findByFilters(region, startDate, endDate, type, spendTime, startTime, maxPeople);
    }
}


