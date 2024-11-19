package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.response.MyProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "사용자 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "프로필 조회", description = "마이페이지 프로필 조회")
    @GetMapping("/mypage/profile")
    public ResponseEntity<ResponseTemplate<?>> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {

        MyProfileResponse response = userService.getMyProfile(Long.valueOf(userDetails.getUsername()));     // userDetails.getUsername() => 유저 Id 반환

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
