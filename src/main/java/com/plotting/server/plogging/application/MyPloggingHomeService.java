package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.dto.response.MyPloggingParticipatedResponse;
import com.plotting.server.plogging.dto.response.MyPloggingScheduledResponse;
import com.plotting.server.plogging.dto.response.MyPloggingSummaryResponse;
import com.plotting.server.plogging.repository.MyPloggingRepository;
import com.plotting.server.plogging.repository.PloggingStarRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPloggingHomeService {

    private final MyPloggingRepository myPloggingRepository;
    private final PloggingUserRepository ploggingUserRepository;
    private final UserRepository userRepository;
    private final PloggingStarRepository ploggingStarRepository;


    public MyPloggingSummaryResponse getPloggingSummary(Long userId) {

        // 유저 정보 가져오기
        User user = getUser(userId);

        // 유저 ID로 참여한 모든 플로깅 데이터 조회
        List<PloggingUser> ploggingUsers = ploggingUserRepository.findAllByUserIdAndIsAssignedTrue(userId);

        // 총 소요 시간 계산
        long totalSpendTime = ploggingUsers.stream()
                .mapToLong(ploggingUser -> ploggingUser.getPlogging().getSpendTime())
                .sum();

        // 시 단위로 변환 (1시간 = 60분)
        long totalSpendTimeInHours = totalSpendTime / 60;

        String nickname = user.getNickname(); // 유저의 닉네임
        String profileImageUrl = user.getProfileImageUrl(); // 유저의 프로필 이미지 URL

        // DTO 반환
        return MyPloggingSummaryResponse.of(
                ploggingUsers.size(),    // 총 플로깅 횟수
                totalSpendTimeInHours,   // 총 플로깅 시간 (시)
                nickname,                // 유저 닉네임
                profileImageUrl          // 유저 프로필 이미지 URL
        );

    }


    public List<MyPloggingParticipatedResponse> getParticipatedPloggings(Long userId) {
        LocalDate currentDate = LocalDate.now();
        // 특정 사용자의 플로깅 정보 조회
        List<Plogging> ploggings = myPloggingRepository.findParticipatedPloggings(userId, currentDate);

        // 참여 인원 수를 포함한 DTO 변환
        return ploggings.stream()
                .map(p -> {
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(p.getId());
                    Boolean isStar = ploggingStarRepository.existsByUserIdAndPloggingId(userId, p.getId());
                    return MyPloggingParticipatedResponse.of(p, currentPeople, isStar);
                })
                .toList();
    }

    public List<MyPloggingScheduledResponse> getScheduledPloggings(Long userId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 특정 사용자의 플로깅 정보 조회
        List<Plogging> ploggings = myPloggingRepository.findScheduledPloggings(userId, currentDateTime);

        // 참여 인원 수를 포함한 DTO 변환
        List<MyPloggingScheduledResponse> response = ploggings.stream()
                .map(p -> {
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(p.getId());

                    Boolean isAssigned = ploggingUserRepository.findByPloggingIdAndUserId(p.getId(), userId)
                            .map(PloggingUser::getIsAssigned)
                            .orElse(false);

                    Boolean isStar = ploggingStarRepository.existsByUserIdAndPloggingId(userId, p.getId());

                    return MyPloggingScheduledResponse.of(p, currentPeople, isAssigned, isStar);
                })
                .toList();
        return response;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

}
