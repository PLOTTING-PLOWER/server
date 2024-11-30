package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.dto.request.MyProfileUpdateRequest;
import com.plotting.server.user.dto.response.DetailProfileResponse;
import com.plotting.server.user.dto.response.MyProfileResponse;
import com.plotting.server.user.dto.response.MypageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "user", description = "사용자 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {
    private final UserService userService;

    @Operation(summary = "프로필 조회", description = "마이페이지 프로필 조회")
    @GetMapping("/main")
    public ResponseEntity<ResponseTemplate<?>> getMyPage(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        MypageResponse response = userService.getMyPage(jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "알람 변경", description = "마이페이지 알람 업데이트")
    @PostMapping("/update-alarm")
    public ResponseEntity<ResponseTemplate<?>> updateAlarm(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails,
            @RequestParam("isAlarmAllowed") Boolean isAlarmAllowed) {
        log.info("--------update-alarm--------"+ isAlarmAllowed);
        userService.updateAlarm(jwtUserDetails.userId(), isAlarmAllowed);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "프로필 조회", description = "마이페이지 프로필 조회")
    @GetMapping("/profile")
    public ResponseEntity<ResponseTemplate<?>> getMyProfile(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        MyProfileResponse response = userService.getMyProfile(jwtUserDetails.userId());     // userDetails.getUsername() => 유저 Id 반환

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "프로필 상세정보 조회", description = "다른 사용자 프로필 상세정보 조회")
    @GetMapping("/{profileId}")
    public ResponseEntity<ResponseTemplate<?>> getDetailProfile(@PathVariable Long profileId, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        DetailProfileResponse response = userService.getDetailProfile(profileId, jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "프로필 수정" , description = "마이페이지 프로필 수정")
    @PatchMapping(value="/profile/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseTemplate<?>> updateMyProfile(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestPart(value = "profileData") MyProfileUpdateRequest myProfileRequest){

        userService.updateMyProfile(jwtUserDetails.userId(), profileImage, myProfileRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }



}
