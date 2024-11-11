package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.dto.response.MyPloggingCreatedListResponse;
import com.plotting.server.plogging.dto.response.MyPloggingCreatedResponse;
import com.plotting.server.plogging.dto.response.MyPloggingUserListResponse;
import com.plotting.server.plogging.dto.response.MyPloggingUserResponse;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPloggingService {

    private final PloggingService ploggingService;
    private final PloggingRepository ploggingRepository;
    private final PloggingUserRepository ploggingUserRepository;

    public MyPloggingCreatedListResponse getMyPloggingCreatedList(Long userId) {
        return MyPloggingCreatedListResponse.from(
                ploggingRepository.findAllByUserId(userId).stream()
                        .map(plogging -> {
                            Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(plogging.getId());
                            Boolean isRecruiting = plogging.getRecruitEndDate().isAfter(LocalDate.now());
                            return MyPloggingCreatedResponse.of(plogging, currentPeople, isRecruiting);
                        })
                        .toList()
        );
    }

    public MyPloggingUserListResponse getMyPloggingUser(Long ploggingId) {
        return MyPloggingUserListResponse.from(
                ploggingUserRepository.findWaitingPloggingUserByPloggingIdWithFetch(ploggingId).stream()
                        .map(MyPloggingUserResponse::from)
                        .toList()
        );
    }

    @Transactional
    public String updatePloggingUser(Long ploggingId, Long ploggingUserId) {
        Plogging plogging = ploggingService.getPlogging(ploggingId);
        PloggingUser ploggingUser = getPloggingUser(ploggingUserId);

        if (ploggingUserRepository.countActivePloggingUsersByPloggingId(ploggingId) >= plogging.getMaxPeople()) {
            return "인원이 초과되었습니다.";
        } else {
            ploggingUser.assign();
            return "승인되었습니다.";
        }
    }

    @Transactional
    public void deleteMyPlogging(Long ploggingId) {
        ploggingRepository.deleteById(ploggingId);
    }

    private PloggingUser getPloggingUser(Long ploggingUserId) {
        return ploggingUserRepository.findById(ploggingUserId)
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND));
    }
}