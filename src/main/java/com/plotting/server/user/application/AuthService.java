package com.plotting.server.user.application;

import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.request.LoginRequest;
import com.plotting.server.user.dto.response.LoginResponse;
import com.plotting.server.user.exception.TokenNotValidateException;
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

import static com.plotting.server.user.exception.errorcode.UserErrorCode.TOKEN_NOT_VALIDATE;
import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.email(),
                    loginRequest.password()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmail(loginRequest.email())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            //JWT 토큰 생성
            String token = jwtUtil.generateToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            // Redis에 Refresh Token 저장
            refreshTokenService.saveRefreshToken(user.getId(), refreshToken);

            // 토큰 및 로그인 성공 메시지 반환
            return LoginResponse.of(token, refreshToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    public String refreshAccessToken(String refreshToken) {
        // Refresh token 검증
        if(!jwtUtil.validateToken(refreshToken)){
            log.error("Invalid Refresh Token");
            throw new TokenNotValidateException(TOKEN_NOT_VALIDATE);
        }

        // refresh 토큰에서 사용자 id 추출
        Long userId = jwtUtil.getIdFromToken(refreshToken);

        //refresh token이 유효한지 확인
        String storedToken = refreshTokenService.getRefreshToken(userId);
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            log.error("Stored token and provided token do not match for userId: {}", userId);
            throw new TokenNotValidateException(TOKEN_NOT_VALIDATE);
        }

        // 새로운 Access Token 생성
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return jwtUtil.generateToken(user);
    }
}



