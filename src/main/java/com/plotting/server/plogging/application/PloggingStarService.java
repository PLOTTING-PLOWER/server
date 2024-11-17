package com.plotting.server.plogging.application;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.dto.response.MyPloggingStarListResponse;
import com.plotting.server.plogging.dto.response.PloggingListResponse;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingStarRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingStarService {
    private final PloggingStarRepository ploggingStarRepository;

    private final PloggingRepository ploggingRepository;

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
}
