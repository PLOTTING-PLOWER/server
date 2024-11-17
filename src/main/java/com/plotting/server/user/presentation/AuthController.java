package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.user.application.AuthService;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.request.LoginRequest;
import com.plotting.server.user.dto.request.SignUpRequest;
import com.plotting.server.user.dto.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    // 회원가입 엔드 포인트 :POST /auth/signUp
    @PostMapping("/signup")
    public ResponseEntity<ResponseTemplate<?>> signUp(@RequestBody SignUpRequest signUpRequest){
        log.info("-----signUp-----");
        userService.registerUser(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(ResponseTemplate.EMPTY_RESPONSE));
    }

    // 일반 로그인 엔드 포인트
    @PostMapping("/login/self")
    public ResponseEntity<ResponseTemplate<?>> login(@Valid @RequestBody LoginRequest loginRequest){
        log.info("-----일반 로그인-----");
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
