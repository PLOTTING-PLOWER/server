package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.dto.response.PloggingDetailResponse;
import com.plotting.server.plogging.dto.response.PloggingUserListResponse;
import com.plotting.server.plogging.dto.response.PloggingUserResponse;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingService {

    private final PloggingRepository ploggingRepository;
    private final PloggingUserRepository ploggingUserRepository;

    public PloggingDetailResponse getPloggingDetail(Long ploggingId) {
        Plogging plogging = getPlogging(ploggingId);
        Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(ploggingId);

        return PloggingDetailResponse.of(plogging, currentPeople);
    }

    public PloggingUserListResponse getPloggingUserList(Long ploggingId) {
        List<PloggingUserResponse> ploggingUserList = ploggingUserRepository.findActivePloggingUsersByPloggingId(ploggingId)
                .stream()
                .map(PloggingUserResponse::from)
                .toList();

        Long maxPeople = ploggingRepository.findMaxPeopleById(ploggingId);
        Long currentPeople = (long) ploggingUserList.size();

        return PloggingUserListResponse.of(currentPeople, maxPeople, ploggingUserList);
    }

    private Plogging getPlogging(Long ploggingId) {
        return ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND));
    }
}