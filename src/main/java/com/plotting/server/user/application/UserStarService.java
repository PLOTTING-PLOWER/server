package com.plotting.server.user.application;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserStar;
import com.plotting.server.user.dto.response.MyUserStarListResponse;
import com.plotting.server.user.dto.response.MyUserStarResponse;
import com.plotting.server.user.repository.UserStarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserStarService {

    private final UserStarRepository userStarRepository;
    private final UserService userService;

    public MyUserStarListResponse getMyUserStarList(Long userId) {
        List<MyUserStarResponse> myUserStarList = userStarRepository.findAllByUserIdWithFetch(userId)
                .stream()
                .map(MyUserStarResponse::from)
                .toList();

        // UserStar -> MyUserStarResponse로 매핑
        return MyUserStarListResponse.from(myUserStarList);
    }

    @Transactional
    public void updateUserStar(Long userId, Long starUserId) {
        Optional<UserStar> userStar = userStarRepository.findByUserIdAndStarUserId(userId, starUserId);

        if(userStar.isPresent()){       // 있으면 삭제
            log.info("Deleting UserStar: {}", userStar);
            userStarRepository.delete(userStar.get());
        }else{      // 없으면 추가
            log.info("Creating UserStar: {}", userStar);
            User user = userService.getUser(userId);
            User starUser = userService.getUser(starUserId);
            userStarRepository.save(UserStar.of(user, starUser));
        }
    }
}
