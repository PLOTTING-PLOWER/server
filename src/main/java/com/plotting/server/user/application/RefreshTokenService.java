package com.plotting.server.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final long REFRESH_TOKEN_EXPIRE = 60 * 60 * 24 * 7; //7일

    // Refresh Token 저장
    public void saveRefreshToken(Long userId, String refreshToken) {
        String key = generateKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRE, TimeUnit.SECONDS);
    }

    // Refresh Token 조회
    public String getRefreshToken(Long userId) {
        String key = generateKey(userId);
        return redisTemplate.opsForValue().get(key);
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(Long userId) {
        String key = generateKey(userId);
        redisTemplate.delete(key);
    }

    // Redis Key 생성
    private String generateKey(Long userId) {
        return "refreshToken:" + userId; // Key 형태: "refreshToken:<userId>"
    }
}
