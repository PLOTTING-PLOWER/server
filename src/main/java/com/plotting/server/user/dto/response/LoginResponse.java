package com.plotting.server.user.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        String refreshToken
) {
    // 정적 팩토리 메서드 추가
    public static LoginResponse of(String token, String refreshToken) {
        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
