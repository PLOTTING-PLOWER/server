package com.plotting.server.user.application;

import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.request.LoginRequest;
import com.plotting.server.user.dto.response.LoginResponse;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest){
        // 이메일& 비밀번호 사용하여 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            loginRequest.email(),
            loginRequest.password()
        );

        // 인증 시도
        Authentication authentication = authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //JWT 토큰 생성
        String token = jwtUtil.generateToken(user);

        // 토큰 및 로그인 성공 메시지 반환
        return new LoginResponse("Login successful", token, user.getEmail());
    }





}



