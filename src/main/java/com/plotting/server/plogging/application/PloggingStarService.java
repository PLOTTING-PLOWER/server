package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingStar;
import com.plotting.server.plogging.dto.response.MyPloggingStarListResponse;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.plotting.server.plogging.exception.PloggingStarNotFoundException;
import com.plotting.server.plogging.exception.errorcode.PloggingErrorCode;
import com.plotting.server.plogging.repository.PloggingStarRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingStarService {
    private final PloggingStarRepository ploggingStarRepository;
    private final UserService userService;
    private final PloggingService ploggingService;
    private final PloggingUserRepository ploggingUserRepository;

    public MyPloggingStarListResponse getMyPloggingStarList(Long userId){
        // 사용자가 즐겨찾기한 플로깅 데이터
        List<PloggingResponse> ploggingResponses = ploggingStarRepository.findPloggingByUserId(userId)
                .stream()
                .map(plogging -> {
                    Long currentPeople = ploggingUserRepository.countActivePloggingUsersByPloggingId(plogging.getId());
                    return PloggingResponse.of(plogging, currentPeople);
                })
                .toList();

        return MyPloggingStarListResponse.from(ploggingResponses);
    }

    @Transactional
    public void deleteUserStar(Long userId, Long ploggingId) {
        PloggingStar ploggingStar = ploggingStarRepository.findByUserIdAndPloggingId(userId, ploggingId)
                .orElseThrow(() -> new PloggingStarNotFoundException(PloggingErrorCode.PLOGGING_STAR_NOT_FOUND));

        log.info("Deleting ploggingStar: {}", ploggingStar);
        ploggingStarRepository.delete(ploggingStar);
    }

    @Transactional
    public void addPloggingStar(Long userId, Long ploggingId) {
        if(ploggingStarRepository.existsByUserIdAndPloggingId(userId, ploggingId)) {
            throw new RuntimeException("PloggingStar already exists");
        }
        Plogging plogging = ploggingService.getPlogging(ploggingId);
        User user = userService.getUser(userId);

        PloggingStar ploggingStar = PloggingStar.builder()
                .user(user)
                .plogging(plogging).build();

        ploggingStarRepository.save(ploggingStar);
    }
}
