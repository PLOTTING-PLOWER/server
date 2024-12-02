package com.plotting.server.user.application;

import com.plotting.server.global.application.S3Service;
import com.plotting.server.plogging.application.MyPloggingService;
import com.plotting.server.plogging.dto.response.PloggingStatsResponse;
import com.plotting.server.ranking.application.RankingService;
import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import com.plotting.server.user.dto.request.MyProfileUpdateRequest;
import com.plotting.server.user.dto.request.SignUpRequest;
import com.plotting.server.user.dto.response.DetailProfileResponse;
import com.plotting.server.user.dto.response.MyProfileResponse;
import com.plotting.server.user.dto.response.MypageResponse;
import com.plotting.server.user.exception.ProfileNotPublicException;
import com.plotting.server.user.exception.UserAlreadyExistsException;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import com.plotting.server.user.repository.UserStarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_ALREADY_EXISTS;
import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserStarRepository userStarRepository;
    private final RankingService rankingService;
    private final MyPloggingService myPloggingService;
    private final S3Service s3Service;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public MypageResponse getMyPage(Long userId) { return MypageResponse.from(getUser(userId)); }

    @Transactional
    public void updateAlarm(Long userId, Boolean isAlarmAllowed) {
        User user = getUser(userId);
        log.info("updateAlarm", isAlarmAllowed);
        user.updateAlarmAllowed(isAlarmAllowed);
        log.info("success updateAlarm");
    }

    public MyProfileResponse getMyProfile(Long userId){
        return MyProfileResponse.from(getUser(userId));
    }

    public DetailProfileResponse getDetailProfile(Long profileId, Long viewerId) {
//        log.info("-----getDetailProfile----- ");
        User user = getUser(profileId);
        if (!user.getIsProfilePublic()) {
            throw new ProfileNotPublicException(USER_NOT_FOUND);
        }
        if(user.getRole() == Role.WITHDRAWN){       // 탈퇴여부
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        Boolean isStar = userStarRepository.existsByUserIdAndStarUserId(viewerId, profileId);

        // 랭킹 가져오기 (랭킹이 없으면 null 반환)
        Ranking ranking;
        ranking = rankingService.getRanking(profileId);


        // 플로깅 통계 가져오기 (없으면 null 반환)
        PloggingStatsResponse ploggingStats = null;
        try {
            ploggingStats = myPloggingService.getPloggingStats(profileId);
        } catch (Exception e) {
            // 예외 발생 시 플로깅 통계를 null로 처리
            log.error("getPloggingStats Error occurred: ", e);
            ploggingStats = null;
        }

        return DetailProfileResponse.of(user, isStar, ranking, ploggingStats);
    }

    @Transactional
    public void updateMyProfile(Long userId, MultipartFile profileImage, MyProfileUpdateRequest myProfileRequest){
        log.info("-----updateMyProfile-----");

        User user = getUser(userId);
        String s3Url = user.getProfileImageUrl();
        if(profileImage!=null && !profileImage.isEmpty()){
            s3Service.deleteFile(user.getProfileImageUrl());    // 기존 이미지 제거
            s3Url = s3Service.uploadFile(profileImage, "profile");   // 이미지 추가
        }

        user.update(myProfileRequest, s3Url);

    }

    @Transactional
    public void saveFcmToken(Long userId, String token){
        log.info("-----saveFcmToken-----");

        User user = getUser(userId);
        if (!token.equals(user.getFcmToken())) {
            user.updateFcmToken(token);
        }
    }
}
