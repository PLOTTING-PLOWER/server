package com.plotting.server.user.dto.request;

import lombok.Builder;

@Builder
public record AccessTokenRequest (
    String accessToken
){}


