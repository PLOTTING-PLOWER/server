package com.plotting.server.user.application;

import com.plotting.server.user.dto.response.MyUserStarListResponse;
import com.plotting.server.user.dto.response.MyUserStarResponse;
import com.plotting.server.user.repository.UserStarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserStarService {

    private final UserStarRepository userStarRepository;

    public MyUserStarListResponse getMyUserStarList(Long userId) {
        List<MyUserStarResponse> myUserStarList = userStarRepository.findAllByUserIdWithUser(userId)
                .stream()
                .map(MyUserStarResponse::from)
                .toList();

        // UserStar -> MyUserStarResponse로 매핑
        return MyUserStarListResponse.of(myUserStarList);
    }
}
