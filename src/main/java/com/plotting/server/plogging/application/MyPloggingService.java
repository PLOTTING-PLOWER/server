package com.plotting.server.plogging.application;

import com.plotting.server.plogging.dto.response.MyPloggingCreatedListResponse;
import com.plotting.server.plogging.dto.response.MyPloggingCreatedResponse;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPloggingService {

    private final UserService userService;
    private final PloggingRepository ploggingRepository;
    private final PloggingUserRepository ploggingUserRepository;

    public MyPloggingCreatedListResponse getMyPloggingCreatedList(Long userId) {
        return new MyPloggingCreatedListResponse(
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
    public void deleteMyPlogging(Long ploggingId) {
        ploggingRepository.deleteById(ploggingId);
    }
}