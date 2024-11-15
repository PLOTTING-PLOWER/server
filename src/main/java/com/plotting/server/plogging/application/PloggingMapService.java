package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.dto.response.PloggingMapResponse;
import com.plotting.server.plogging.repository.PloggingMapRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PloggingMapService {

    private final PloggingMapRepository ploggingMapRepository;
    private final PloggingUserRepository ploggingUserRepository;

    public PloggingMapService(PloggingMapRepository ploggingMapRepository, PloggingUserRepository ploggingUserRepository) {
        this.ploggingMapRepository = ploggingMapRepository;
        this.ploggingUserRepository = ploggingUserRepository;
    }

    public List<PloggingMapResponse> getPloggingInBounds(BigDecimal lat1, BigDecimal lon1, int zoom) {
        if (zoom >= 15) {
            // 줌 레벨이 높으면 세부 정보 반환
            List<Plogging> ploggings = ploggingMapRepository.findAllWithinRadius(lat1, lon1, 2.0);
            return ploggings.stream()
                    .map(p -> {
                        Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(p.getId());
                        return PloggingMapResponse.forbasic(p, currentPeople);
                    })
                    .collect(Collectors.toList());
        } else {
            // 줌 레벨이 낮으면 클러스터 정보 반환
            List<Object[]> clusters = ploggingMapRepository.findClustersInRadius(lat1, lon1, calculateRadius(zoom));
            return clusters.stream()
                    .map(clusterData -> {
                        BigDecimal averageLatitude = (BigDecimal) clusterData[0];
                        BigDecimal averageLongitude = (BigDecimal) clusterData[1];
                        Integer count = ((Number) clusterData[2]).intValue();
                        return PloggingMapResponse.forCluster(null, null, averageLatitude, averageLongitude, count);
                    })
                    .collect(Collectors.toList());
        }
    }

    private double calculateRadius(int zoom) {
        // 줌 레벨에 따라 반경 계산 로직 구현
        return Math.pow(2, 15 - zoom);
    }
}
