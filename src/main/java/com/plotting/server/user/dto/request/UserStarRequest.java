package com.plotting.server.user.dto.request;

import lombok.Builder;

@Builder
public record UserStarRequest(
        Long userId,
        Long starUserId
){
}
