package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.request.PloggingRequest;
import com.plotting.server.plogging.dto.response.PloggingDetailResponse;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.plotting.server.plogging.dto.response.PloggingUserListResponse;
import com.plotting.server.plogging.dto.response.PloggingUserResponse;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.plotting.server.plogging.domain.type.PloggingType.ASSIGN;
import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingService {

    private final UserService userService;
    private final PloggingRepository ploggingRepository;
    private final PloggingUserRepository ploggingUserRepository;

    //플로깅 모임 등록
    @Transactional
    public void createPlogging(PloggingRequest ploggingRequest) {
        ploggingRepository.save(ploggingRequest.toEntity(BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
        //(설계) 위도, 경도 받아오기
        //외부 API로 위도, 경도 받아오기 -> 파사드 패턴 이용해서 (외부 서버 갔다오는 동안) 리소스(connection) 누수 : connection-pool
        //api가 먼저 나오고
        //비지니스 로직 나오고
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
    public String joinPlogging(Plogging plogging, Long userId) {
        User user = userService.getUser(userId);

        Boolean isAssigned = !plogging.getPloggingType().equals(ASSIGN);

        ploggingUserRepository.saveAndFlush(PloggingUser.of(plogging, user, isAssigned));

        if (isAssigned) {
            return "참여 승인되었습니다.";
        } else {
            return "참여 요청이 완료되었습니다.";
        }
    }

    public Plogging getPlogging(Long ploggingId) {
        return ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException(PLOGGING_NOT_FOUND));
    }
}