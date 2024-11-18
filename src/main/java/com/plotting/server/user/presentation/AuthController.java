package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.application.AuthService;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.request.LoginRequest;
import com.plotting.server.user.dto.request.SignUpRequest;
import com.plotting.server.user.dto.request.TestTokenRequest;
import com.plotting.server.user.dto.response.LoginResponse;
import com.plotting.server.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Auth", description = "인증 권련 api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // 회원가입 엔드 포인트 :POST /auth/signUp
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseTemplate<?>> signUp(@RequestBody SignUpRequest signUpRequest){
        log.info("-----signUp-----");
        userService.registerUser(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    // 일반 로그인 엔드 포인트
    @Operation(summary = "로그인" ,description = "일반 회원 로그인")
    @PostMapping("/login/self")
    public ResponseEntity<ResponseTemplate<?>> login(@Valid @RequestBody LoginRequest loginRequest){
        log.info("-----self login-----");
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    // 토큰 생성 API
    @Operation(summary = "jwt 토큰 생성", description = "테스트용 jwt 토큰 생성")
    @PostMapping("test-token")
    public ResponseEntity<ResponseTemplate<?>> generateToken(@RequestBody TestTokenRequest tokenRequest){
        log.info("----creadte jwt test token ----");

        User user =userRepository.save(User.createTestUser(tokenRequest.nickname()));

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseTemplate.from(token));

    }
}
