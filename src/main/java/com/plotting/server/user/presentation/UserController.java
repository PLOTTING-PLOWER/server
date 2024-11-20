package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.dto.request.MyProfileUpdateRequest;
import com.plotting.server.user.dto.response.DetailProfileResponse;
import com.plotting.server.user.dto.response.MyProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "user", description = "사용자 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "프로필 조회", description = "마이페이지 프로필 조회")
    @GetMapping("/mypage/profile")
    public ResponseEntity<ResponseTemplate<?>> getMyProfile(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        MyProfileResponse response = userService.getMyProfile(Long.valueOf(jwtUserDetails.userId()));     // userDetails.getUsername() => 유저 Id 반환

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "프로필 상세정보 조회", description = "다른 사용자 프로필 상세정보 조회")
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<ResponseTemplate<?>> getDetailProfile(@PathVariable Long profileId, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        DetailProfileResponse response = userService.getDetailProfile(profileId, Long.valueOf(jwtUserDetails.userId()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "프로필 수정" , description = "마이페이지 프로필 수정")
    @PatchMapping("/mypage/profile/edit")
    public ResponseEntity<ResponseTemplate<?>> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestBody MyProfileUpdateRequest myProfileRequest){

        userService.updateMyProfile(Long.valueOf(userDetails.getUsername()), profileImage, myProfileRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }


}
