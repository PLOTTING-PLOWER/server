package com.plotting.server.user.domain.UserType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {

    NAVER("naver"),
    GOOGLE("google"),
    SELF("self"),
    ;

    private final String loginType;
}
