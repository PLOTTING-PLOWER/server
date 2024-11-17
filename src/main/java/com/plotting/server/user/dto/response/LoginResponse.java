package com.plotting.server.user.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String message,
        String token,
        String email
) {
    // 정적 팩토리 메서드 추가
    public static LoginResponse of(String message, String token, String email) {
        return LoginResponse.builder()
                .message(message)
                .token(token)
                .email(email)
                .build();
    }
}
