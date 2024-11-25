package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.dto.response.MyPloggingParticipatedResponse;
import com.plotting.server.plogging.dto.response.MyPloggingScheduledResponse;
import com.plotting.server.plogging.dto.response.MyPloggingSummaryResponse;
import com.plotting.server.plogging.repository.MyPloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPloggingHomeService {

    private final MyPloggingRepository myPloggingRepository;
    private final PloggingUserRepository ploggingUserRepository;

    public MyPloggingSummaryResponse getPloggingSummary(Long userId) {
        // 유저 ID로 참여한 모든 플로깅 데이터 조회
        List<PloggingUser> ploggingUsers = ploggingUserRepository.findAllByUserIdAndIsAssignedTrue(userId);

        // 총 소요 시간 계산
        long totalSpendTime = ploggingUsers.stream()
                .mapToLong(ploggingUser -> {
                    Plogging plogging = ploggingUser.getPlogging();
                    return (plogging != null) ? plogging.getSpendTime() : 0L; // null 안전 처리
                })
                .sum();

        // 시 단위로 변환 (1시간 = 60분)
        long totalSpendTimeInHours = totalSpendTime / 60;

        // 유저 정보 가져오기
        PloggingUser user = ploggingUserRepository.findByUserId(userId);  // 가정: 유저를 PloggingUser에서 찾기
        String nickname = user.getUser().getNickname(); // 유저의 닉네임
        String profileImageUrl = user.getUser().getProfileImageUrl(); // 유저의 프로필 이미지 URL

        // MyPloggingSummaryResponse 객체 생성 및 반환
        return MyPloggingSummaryResponse.builder()
                .totalPloggingCount(ploggingUsers.size())
                .totalSpendTime(totalSpendTimeInHours)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }


    public List<MyPloggingParticipatedResponse> getParticipatedPloggings(long userId) {
        LocalDate currentDate = LocalDate.now();
        // 특정 사용자의 플로깅 정보 조회
        List<Plogging> ploggings = myPloggingRepository.findParticipatedPloggings(userId, currentDate);

        // 참여 인원 수를 포함한 DTO 변환
        return ploggings.stream()
                .map(p -> {
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(p.getId());
                    return MyPloggingParticipatedResponse.of(p, currentPeople);
                })
                .collect(Collectors.toList());
    }

    public List<MyPloggingScheduledResponse> getScheduledPloggings(long userId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 특정 사용자의 플로깅 정보 조회
        List<Plogging> ploggings = myPloggingRepository.findScheduledPloggings(userId, currentDateTime);

        // 참여 인원 수를 포함한 DTO 변환
        List<MyPloggingScheduledResponse> responseList = ploggings.stream()
                .map(p -> {
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(p.getId());

                    Boolean isAssigned = ploggingUserRepository.findByPloggingIdAndUserId(p.getId(), userId)
                            .map(PloggingUser::getIsAssigned)
                            .orElse(false);

                    return MyPloggingScheduledResponse.of(p, currentPeople, isAssigned);
                })
                .collect(Collectors.toList());

        // 데이터를 출력하는 프린트문 추가
        System.out.println("Scheduled Ploggings for User ID " + userId + ":");
        System.out.println("Fetched responseList: " + responseList);

        return responseList;
    }


}
