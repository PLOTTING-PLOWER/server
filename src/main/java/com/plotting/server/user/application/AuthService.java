package com.plotting.server.user.application;

import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import com.plotting.server.user.dto.request.LoginRequest;
import com.plotting.server.user.dto.request.SignUpRequest;
import com.plotting.server.user.dto.response.LoginResponse;
import com.plotting.server.user.exception.TokenNotValidateException;
import com.plotting.server.user.exception.UserAlreadyExistsException;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            log.info("1. Attempting authentication for email: {}", loginRequest);

            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.email(),
                    loginRequest.password()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("2. Attempting authentication for email: {}", loginRequest);

            User user = userRepository.findByEmail(loginRequest.email())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            log.info("User found: {}, Role: {}", user.getEmail(), user.getRole());

            // 탈퇴한 회원 일때,
            if(user.getRole()==Role.WITHDRAWN) {
                throw new UserNotFoundException(USER_NOT_FOUND);
            }

            //JWT 토큰 생성
            String token = jwtUtil.generateToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            // 토큰 및 로그인 성공 메시지 반환
            return LoginResponse.of(token, refreshToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
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

    public String refreshAccessToken(String refreshToken) {
        // Refresh token 검증
        if(!jwtUtil.validateToken(refreshToken)){
            log.error("Invalid Refresh Token");
            throw new TokenNotValidateException(TOKEN_NOT_VALIDATE);
        }

        // refresh 토큰에서 사용자 id 추출
        Long userId = jwtUtil.getIdFromToken(refreshToken);

        // 새로운 Access Token 생성
        User user = userService.getUser(userId);

        return jwtUtil.generateToken(user);
    }

    @Transactional
    public void updateUserRoleWithdraw(Long userId) {
        userService.getUser(userId).updateRole(Role.WITHDRAWN);
    }
}



