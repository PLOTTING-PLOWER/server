package com.plotting.server.user.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String message,
        String token,
        String email
) {
}
