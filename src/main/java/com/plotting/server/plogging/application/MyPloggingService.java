package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.dto.request.PloggingUpdateRequest;
import com.plotting.server.plogging.dto.response.*;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.plogging.exception.PloggingUserNotFoundException;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;
import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPloggingService {

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

    @Transactional
    public void updatePlogging(Long ploggingId, PloggingUpdateRequest request) {
        Plogging plogging = getPlogging(ploggingId);
        plogging.update(request);
    }

    @Transactional
    public void deleteMyPlogging(Long ploggingId) {
        ploggingRepository.deleteById(ploggingId);
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
        Plogging plogging = getPlogging(ploggingId);
        PloggingUser ploggingUser = getPloggingUser(ploggingUserId);

        if (ploggingUserRepository.countActivePloggingUsersByPloggingId(ploggingId) >= plogging.getMaxPeople()) {
            return "인원이 초과되었습니다.";
        } else {
            ploggingUser.assign();
            return "승인되었습니다.";
        }
    }

    @Transactional
    public void deletePloggingUser(Long ploggingUserId) {
        ploggingUserRepository.deleteById(ploggingUserId);
    }

    public MonthlyPloggingListResponse getMonthlyPlogging(Long userId) {
        return MonthlyPloggingListResponse.from(ploggingUserRepository.findMonthlyPloggingStatsByUserId(userId));
    }

    public Plogging getPlogging(Long ploggingId) {
        return ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND));
    }

    public PloggingUser getPloggingUser(Long ploggingUserId) {
        return ploggingUserRepository.findById(ploggingUserId)
                .orElseThrow(() -> new PloggingUserNotFoundException(PLOGGING_USER_NOT_FOUND));
    }

    public PloggingStatsResponse getPloggingStats(Long ploggingUserId){
        List<PloggingTimeResponse> ploggingTimes = ploggingRepository.findAllPloggingTimeByUserId(ploggingUserId);

        long totalPloggingNumber = ploggingTimes.stream()
                .filter(p -> p.startTime().plusMinutes(p.spendTime()).isBefore(LocalDateTime.now())) // 종료된 플로깅 필터링
                .count();

        long totalPloggingTime = ploggingTimes.stream()
                .filter(p -> p.startTime().plusMinutes(p.spendTime()).isBefore(LocalDateTime.now())) // 종료된 플로깅 필터링
                .mapToLong(PloggingTimeResponse::spendTime) // spendTime 합산
                .sum();

        return PloggingStatsResponse.builder()
                .totalPloggingNumber(totalPloggingNumber)
                .totalPloggingTime(totalPloggingTime)
                .build();
    }
}