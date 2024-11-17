package com.plotting.server.user.dto.request;

import lombok.Builder;

@Builder
public record RemoveUserStarRequest (
        Long userId,
        Long userStarId
){
}
