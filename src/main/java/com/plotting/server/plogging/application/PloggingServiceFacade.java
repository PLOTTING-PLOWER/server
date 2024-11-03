package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.dto.request.PloggingRequest;
import com.plotting.server.plogging.dto.response.GeocodeResponse;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.plotting.server.plogging.domain.type.PloggingType.ASSIGN;
import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;
import static com.plotting.server.plogging.util.PloggingConstants.ALREADY_COMPLETE;
import static com.plotting.server.plogging.util.PloggingConstants.FULL_RECRUIT;
import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class PloggingServiceFacade {

    private final PloggingService ploggingService;
    private final UserRepository userRepository;
    private final PloggingRepository ploggingRepository;
    private final PloggingUserRepository ploggingUserRepository;
    private final GeocodeService geocodeService;

    public void createPlogging(Long userId, PloggingRequest ploggingRequest) {
        GeocodeResponse start = geocodeService.getGeocode(ploggingRequest.startLocation());
        GeocodeResponse dest = geocodeService.getGeocode(ploggingRequest.endLocation());

        ploggingService.createPlogging(userId, ploggingRequest, start, dest);
    }

    public String joinPlogging(Long ploggingId, Long userId) {
        String response;

        User user = getUser(userId);
        Plogging plogging = getPlogging(ploggingId);

        // 참여중인 플로깅이 있는지 확인
        if (existPloggingUser(ploggingId, userId)) {
            return ALREADY_COMPLETE.getMessage();
        }

        // 승인제일 경우
        if (plogging.getPloggingType() == ASSIGN) {
            response = ploggingService.joinAssignPlogging(plogging, user);
        } else {  // 선착순일 경우
            try {
                ploggingUserRepository.getLock(ploggingId.toString());

                log.info("lock : ploggingId: {}, userId: {}", ploggingId, userId);

                // 플로깅 현재 참여 인원이 최대 인원보다 작을 때만 참여 가능하도록 수행
                response = validatePloggingUserNum(plogging) ?
                        ploggingService.joinDirectPlogging(plogging, user) : FULL_RECRUIT.getMessage();
            } finally {
                ploggingUserRepository.releaseLock(ploggingId.toString());
            }
        }

        return response;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    private Plogging getPlogging(Long ploggingId) {
        return ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND));
    }

    private boolean existPloggingUser(Long ploggingId, Long userId) {
        return ploggingUserRepository.existsByPloggingIdAndUserId(ploggingId, userId);
    }

    private boolean validatePloggingUserNum(Plogging plogging) {
        return ploggingUserRepository.countActivePloggingUsersByPloggingId(plogging.getId()) < plogging.getMaxPeople();
    }
}