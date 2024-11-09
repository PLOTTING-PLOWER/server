package com.plotting.server.plogging.dto.response;

public record PlowerResponse(
        Long userId,
        String profileImageUrl
) {
    public static PlowerResponse of(Long userId, String profileImageUrl) {
        return new PlowerResponse(userId, profileImageUrl);
    }
}
