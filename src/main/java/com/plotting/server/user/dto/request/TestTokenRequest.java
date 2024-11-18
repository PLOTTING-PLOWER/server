package com.plotting.server.user.dto.request;

import com.plotting.server.user.domain.UserType.Role;
import lombok.Builder;

@Builder
public record TestTokenRequest(
        String nickname    // 유저 이메일
){
}