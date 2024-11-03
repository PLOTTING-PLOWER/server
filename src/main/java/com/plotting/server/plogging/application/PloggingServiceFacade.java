package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.dto.request.PloggingRequest;
import com.plotting.server.plogging.dto.response.GeocodeResponse;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PloggingServiceFacade {

    private final PloggingService ploggingService;
    private final PloggingUserRepository ploggingUserRepository;
    private final GeocodeService geocodeService;

    public void createPlogging(Long userId, PloggingRequest ploggingRequest) {
        GeocodeResponse start = geocodeService.getGeocode(ploggingRequest.startLocation());
        GeocodeResponse dest = geocodeService.getGeocode(ploggingRequest.endLocation());

        ploggingService.createPlogging(userId, ploggingRequest, start, dest);
    }

    @Transactional
    public String joinPlogging(Long ploggingId, Long userId) {
        Plogging plogging = ploggingService.getPlogging(ploggingId);

        String response;

        // 참여중인 플로깅이 있는지 확인
        if (ploggingUserRepository.existsByPloggingIdAndUserId(ploggingId, userId)) {
            response = "참가중이거나 참가 신청을 완료한 플로깅입니다.";
        }
        // 플로깅 현재 참여 인원이 최대 인원보다 작을 때만 참여 가능하도록 수행
        else if (ploggingUserRepository.countActivePloggingUsersByPloggingId(ploggingId) < plogging.getMaxPeople()) {
            try {
                ploggingUserRepository.getLock(ploggingId.toString());
                response = ploggingService.joinPlogging(plogging, userId);
            } finally {
                ploggingUserRepository.releaseLock(ploggingId.toString());
            }
        }
        // 플로깅 참가 인원이 가득 찼을 때
        else {
            response = "플로깅 모집 인원이 마감되었습니다.";
        }

        return response;
    }
}