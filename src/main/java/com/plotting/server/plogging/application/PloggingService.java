package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.request.PloggingRequest;
import com.plotting.server.plogging.dto.response.*;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;
import static com.plotting.server.plogging.util.PloggingConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PloggingRepository ploggingRepository;
    private final PloggingUserRepository ploggingUserRepository;

    //플로깅 홈
    @Transactional
    public void getHome(Long userId, Long ploggingId) {

        PloggingListResponse ploggingStar = getPloggingStar(ploggingId);

        for (PloggingResponse ploggingResponse : ploggingStar.ploggingResponseList()) {
            log.info("ploggingResponse: {}", ploggingResponse);
        }

        PlowerListResponse plowerStar = getPlowerStar(userId);

        for (PlowerResponse plowerResponse : plowerStar.plowerResponseList()) {
            log.info("plowerResponse: {}", plowerResponse);
        }
    }

    //플로워 즐겨찾기
    private PlowerListResponse getPlowerStar(Long userId) {
        List<PlowerResponse> userList = userRepository.findTop5Users().stream()
                .map(user -> PlowerResponse.from(user.getId(), user.getProfileImageUrl()))
                .toList();

        return PlowerListResponse.from(userList);
    }

    // 플로깅 즐겨찾기
    private PloggingListResponse getPloggingStar(Long ploggingId) {
        Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(ploggingId);

        List<PloggingResponse> ploggingList = ploggingRepository.findTop3Ploggings().stream()
                .map(ploggingResponse -> PloggingResponse.from(ploggingResponse, currentPeople))
                .toList();

        return PloggingListResponse.from(currentPeople, ploggingList);
    }

    //플로깅 모임 등록
    @Transactional
    public void createPlogging(Long userId, PloggingRequest ploggingRequest, GeocodeResponse start, GeocodeResponse dest) {
        User user = userService.getUser(userId);
        Plogging plogging = ploggingRequest.toPlogging(user, start.getLatitude(), start.getLongitude());
        ploggingRepository.save(plogging);
    }

    // 필터링 검색
    public List<PloggingResponse> findListByFilter(String region, LocalDate startDate, LocalDate endDate, PloggingType type,
                                                   Long spendTime, LocalDateTime startTime, Long maxPeople) {

        return ploggingRepository.findByFilters(region, startDate, endDate, type, spendTime, startTime, maxPeople);
    }

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String joinDirectPlogging(Plogging plogging, User user) {
        ploggingUserRepository.save(PloggingUser.of(plogging, user, true));
        return DIRECT_COMPLETE.getMessage();
    }

    @Transactional
    public String joinAssignPlogging(Plogging plogging, User user) {
        ploggingUserRepository.save(PloggingUser.of(plogging, user, false));
        return ASSIGN_COMPLETE.getMessage();
    }

    public Plogging getPlogging(Long ploggingId) {
        return ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND));
    }
}