package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PloggingServiceFacade {

    private final PloggingService ploggingService;
    private final PloggingUserRepository ploggingUserRepository;

    public String joinPlogging(Long ploggingId, Long userId) {
        String response;

        try {
            ploggingUserRepository.getLock(ploggingId.toString());

            Plogging plogging = ploggingService.getPlogging(ploggingId);
            log.info("플로깅 참가 시도");

            // 참여중인 플로깅이 있는지 확인
            if (ploggingUserRepository.existsByPloggingIdAndUserId(ploggingId, userId)) {
                response = "참가중이거나 참가 신청을 완료한 플로깅입니다.";
            }// 플로깅 현재 참여 인원이 최대 인원보다 작을 때만 참여 가능하도록 수행
            else if (ploggingUserRepository.countActivePloggingUsersByPloggingId(ploggingId) < plogging.getMaxPeople()) {
                response = ploggingService.joinPlogging(plogging, userId);
            }// 플로깅 참가 인원이 가득 찼을 때
            else {
                response = "플로깅 모집 인원이 마감되었습니다.";
            }

        } finally {
            ploggingUserRepository.releaseLock(ploggingId.toString());
            log.info("플로깅 참가 시도 완료");
        }


        return response;
    }
}