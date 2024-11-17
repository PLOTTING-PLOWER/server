package com.plotting.server.user.application;

import com.plotting.server.user.domain.UserStar;
import com.plotting.server.user.dto.response.MyUserStarListResponse;
import com.plotting.server.user.dto.response.MyUserStarResponse;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.exception.UserStarNotFoundException;
import com.plotting.server.user.repository.UserStarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_STAR_NOT_FOUND;

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

    @Transactional
    public void removeUserStar(Long userId, Long userStarId) {
        UserStar userStar = userStarRepository.findByUserIdAndStarUserId(userId, userStarId)
                .orElseThrow(() -> new UserStarNotFoundException(USER_STAR_NOT_FOUND));

        log.info("Deleting UserStar: {}", userStar);
        userStarRepository.delete(userStar);
    }
}
