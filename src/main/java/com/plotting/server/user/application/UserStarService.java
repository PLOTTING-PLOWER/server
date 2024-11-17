package com.plotting.server.user.application;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserStar;
import com.plotting.server.user.dto.response.MyUserStarListResponse;
import com.plotting.server.user.dto.response.MyUserStarResponse;
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
    private final UserService userService;


    public MyUserStarListResponse getMyUserStarList(Long userId) {
        List<MyUserStarResponse> myUserStarList = userStarRepository.findAllByUserIdWithUser(userId)
                .stream()
                .map(MyUserStarResponse::from)
                .toList();

        // UserStar -> MyUserStarResponse로 매핑
        return MyUserStarListResponse.of(myUserStarList);
    }

    @Transactional
    public void deleteUserStar(Long userId, Long starUserId) {
        UserStar userStar = userStarRepository.findByUserIdAndStarUserId(userId, starUserId)
                .orElseThrow(() -> new UserStarNotFoundException(USER_STAR_NOT_FOUND));

        log.info("Deleting UserStar: {}", userStar);
        userStarRepository.delete(userStar);
    }

    @Transactional
    public void addUserStar(Long userId, Long starUserId) {
        if(userStarRepository.existsByUserIdAndStarUserId(userId, starUserId)){
            throw new RuntimeException("이미 즐겨찾기에 추가된 사용자입니다.");
        }
        User user = userService.getUser(userId);
        User starUser = userService.getUser(starUserId);

        UserStar userStar= UserStar.builder()
                .user(user)
                .starUser(starUser)
                .build();

        userStarRepository.save(userStar);
    }
}
