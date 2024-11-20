package com.plotting.server.user.application;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Use loadUserById instead."); // 이메일 기반 검색 사용 안함
    }

    public JwtUserDetails loadUserByUserId(Long userId) {
        log.info("Attempting to load user by id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("user not found userId "));

        return  JwtUserDetails.from(user);
    }

    public JwtUserDetails loadUserFromClaims(Claims claims) {
        log.info("Attempting to load user by id: {}", claims);

        return  JwtUserDetails.from(claims);
    }
}
