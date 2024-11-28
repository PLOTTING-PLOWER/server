package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.dto.response.PloggingMapResponse;
import com.plotting.server.plogging.repository.PloggingMapRepository;
import com.plotting.server.plogging.repository.PloggingStarRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PloggingMapService {

    private final PloggingMapRepository ploggingMapRepository;
    private final PloggingUserRepository ploggingUserRepository;
    private final PloggingStarRepository ploggingStarRepository;

    @Transactional(readOnly = true)
    public List<PloggingMapResponse> getPloggingInBounds(BigDecimal lat1, BigDecimal lon1, int zoom, Long userId) {
        // 줌 레벨이 높으면 세부 정보 반환
        List<Plogging> ploggings = ploggingMapRepository.findAllWithinRadius(lat1, lon1, 5.0);
        return ploggings.stream()
                .map(p -> {
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(p.getId());
                    Boolean isStar = ploggingStarRepository.existsByUserIdAndPloggingId(userId,p.getId());
                    return PloggingMapResponse.of(p, currentPeople, isStar);
                })
                .collect(Collectors.toList());
    }
}
