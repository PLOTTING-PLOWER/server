package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.application.AuthService;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.dto.request.AccessTokenRequest;
import com.plotting.server.user.dto.request.LoginRequest;
import com.plotting.server.user.dto.request.RefreshTokenRequest;
import com.plotting.server.user.dto.request.SignUpRequest;
import com.plotting.server.user.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="Auth", description = "인증 관련 api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // 회원가입 엔드 포인트 :POST /auth/signUp
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseTemplate<?>> signUp(@RequestBody SignUpRequest signUpRequest){
        log.info("-----signUp-----");
        authService.registerUser(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 체크")
    @GetMapping("/validation/nickname")
    public ResponseEntity<ResponseTemplate<?>> checkNickname(@RequestParam String nickname){
        log.info("-----check nickname-----");
        boolean isAvailable = authService.isNicknameAvailable(nickname);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(isAvailable));
    }

    // 일반 로그인 엔드 포인트
    @Operation(summary = "로그인" ,description = "일반 회원 로그인")
    @PostMapping("/login/self")
    public ResponseEntity<ResponseTemplate<?>> login(@RequestBody LoginRequest loginRequest){
        log.info("-----self login-----");
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "네이버 로그인", description = "네이버 로그인 엑세스 토큰 받기")
    @PostMapping("/login/naver")
    public ResponseEntity<ResponseTemplate<?>> naverLogin(@RequestBody AccessTokenRequest request){
        String accessToken = request.accessToken();

        LoginResponse response = authService.loginWithNaver(accessToken);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴하기")
    @DeleteMapping("/withdraw")
    public ResponseEntity<ResponseTemplate<?>> withdraw(@AuthenticationPrincipal JwtUserDetails jwtUserDetails){
        authService.updateUserRoleWithdraw(jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    // 토큰 생성 API
    @Operation(summary = "jwt 토큰 생성", description = "테스트용 jwt 토큰 생성")
    @PostMapping("/test-token")
    public ResponseEntity<ResponseTemplate<?>> generateToken(@RequestParam Long userId){
        log.info("----creadte jwt test token ----");

        String token = jwtUtil.generateToken(userService.getUser(userId));
        //String refreshToken = jwtUtil.generateRefreshToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseTemplate.from(token));
    }

    @Operation(summary = "Access Token 재발급", description = "유효한 Refresh Token을 사용해 Access Token을 재발급")
    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseTemplate<?>> refreshAccessToken(@RequestBody RefreshTokenRequest request){
        String response = authService.refreshAccessToken(request.refreshToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
