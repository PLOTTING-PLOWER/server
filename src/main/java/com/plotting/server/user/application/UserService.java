package com.plotting.server.user.application;

import com.plotting.server.plogging.application.MyPloggingService;
import com.plotting.server.plogging.dto.response.PloggingStatsResponse;
import com.plotting.server.ranking.application.RankingService;
import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.request.SignUpRequest;
import com.plotting.server.user.dto.response.DetailProfileResponse;
import com.plotting.server.user.dto.response.MyProfileResponse;
import com.plotting.server.user.exception.ProfileNotPublicException;
import com.plotting.server.user.exception.UserAlreadyExistsException;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import com.plotting.server.user.repository.UserStarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_ALREADY_EXISTS;
import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserStarRepository userStarRepository;
    private final RankingService rankingService;
    private final MyPloggingService myPloggingService;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public MyProfileResponse getMyProfile(Long userId){
        return MyProfileResponse.from(getUser(userId));
    }

    @Transactional
    public User registerUser(SignUpRequest signUpRequest) {
        log.info("Registering user with email: {}", signUpRequest.email());
        // 이메일 중복 체크
        if (userRepository.existsByEmail(signUpRequest.email())){
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }

        User user = signUpRequest.toUser(passwordEncoder);

        // DB에 사용자 정보 저장
        return userRepository.save(user);
    }

    public DetailProfileResponse getDetailProfile(Long profileId, Long viewerId) {
        log.info("-----getDetailProfile----- ");
        User user = getUser(profileId);
        if (!user.getIsProfilePublic()) {
            throw new ProfileNotPublicException(USER_NOT_FOUND);
        }

        Boolean isStar = userStarRepository.existsByUserIdAndStarUserId(viewerId, profileId);
        Ranking ranking = rankingService.getRanking(profileId);
        PloggingStatsResponse ploggingStats= myPloggingService.getPloggingStats(profileId);

        return DetailProfileResponse.of(user, isStar, ranking, ploggingStats.totalPloggingNumber(), ploggingStats.totalPloggingTime());
    }
}
