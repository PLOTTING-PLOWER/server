package com.plotting.server.user.application;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.dto.request.SignUpRequest;
import com.plotting.server.user.exception.UserAlreadyExistsException;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_ALREADY_EXISTS;
import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
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
}
