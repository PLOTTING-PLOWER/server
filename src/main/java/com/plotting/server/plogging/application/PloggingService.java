package com.plotting.server.plogging.application;

import com.plotting.server.alarm.application.AlarmService;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.request.PloggingRequest;
import com.plotting.server.plogging.dto.response.*;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingStarRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.plotting.server.plogging.domain.QPlogging.plogging;
import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;
import static com.plotting.server.plogging.util.PloggingConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingService {

    private final UserService userService;
    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final PloggingRepository ploggingRepository;
    private final PloggingUserRepository ploggingUserRepository;
    private final PloggingStarRepository  ploggingStarRepository;

    //플로깅 검색-> 상단 제일 첫번째 1개만 검색
    public PloggingGetStarResponse getPloggingWithTitle(String title) {
        return ploggingRepository.findByTitleContaining(title)
                .stream()
                .map(plogging -> {
                    Boolean isStar = ploggingStarRepository.existsByUserIdAndPloggingId(plogging.getUser().getId(), plogging.getId());
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(plogging.getId());
                    return PloggingGetStarResponse.of(plogging, currentPeople ,isStar);
                })
                .findFirst() // 첫 번째 항목을 선택
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND)); // 예외 처리
    }

    //플로깅 홈
    public HomeResponse getHome(Long userId) {
        PloggingGetStarListResponse plogging = getPloggingStar();
        PlowerListResponse plowerStar = getPlowerStar();
        User user = userService.getUser(userId);

        return HomeResponse.of(plogging, plowerStar, user);
    }

    //플로워 즐겨찾기
    private PlowerListResponse getPlowerStar() {
        List<PlowerResponse> userList = userRepository.findTop5Users().stream()
                .map(user -> PlowerResponse.of(user.getId(), user.getProfileImageUrl()))
                .toList();

        return PlowerListResponse.from(userList);
    }

    // 플로깅 즐겨찾기
    private PloggingGetStarListResponse getPloggingStar() {
        List<PloggingGetStarResponse> ploggingList = ploggingRepository.findTop3Ploggings().stream()
                .map(plogging -> {
                    Boolean isStar = ploggingStarRepository.existsByUserIdAndPloggingId(plogging.getUser().getId(), plogging.getId());
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(plogging.getId());
                    return PloggingGetStarResponse.of(plogging, currentPeople, isStar);
                })
                .toList();

        return PloggingGetStarListResponse.from(ploggingList);
    }


    //플로깅 모임 등록
    @Transactional
    public void createPlogging(Long userId, PloggingRequest ploggingRequest, GeocodeResponse start, GeocodeResponse dest) {
        User user = userService.getUser(userId);
        Plogging plogging = ploggingRequest.toPlogging(user, start.getLatitude(), start.getLongitude());
        ploggingRepository.save(plogging);
        plogging.calculateEndTime();
    }

    // 필터링 검색
    public List<PloggingResponse> findListByFilter(String region, LocalDate startDate, LocalDate endDate, PloggingType type,
                                                   Long spendTime, LocalDateTime startTime, Long maxPeople) {

        List<PloggingResponse> result = ploggingRepository.findByFilters(region, startDate, endDate, type, spendTime, startTime, maxPeople);
        log.info("Found {} ploggings with the given filter", result.size());

        return result;
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
        alarmService.saveDirectAlarm(user, plogging.getUser(), plogging);
        return DIRECT_COMPLETE.getMessage();
    }

    @Transactional
    public String joinAssignPlogging(Plogging plogging, User user) {
        ploggingUserRepository.save(PloggingUser.of(plogging, user, false));
        alarmService.saveAssignAlarm(user, plogging.getUser(), plogging);
        return ASSIGN_COMPLETE.getMessage();
    }

    public Plogging getPlogging(Long ploggingId) {
        return ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND));
    }

    // ploggingId와 userId로 해당 플로깅 유저 삭제
    public void removeUserFromPlogging(Long ploggingId, Long userId) {
        ploggingUserRepository.deleteByPloggingIdAndUserId(ploggingId, userId);
    }
}