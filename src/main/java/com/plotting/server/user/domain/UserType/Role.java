package com.plotting.server.user.domain.UserType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("guest"),
    USER("user"),
    ADMIN("admin"),
    WITHDRAWN("withdrawn");
    ;

    private final String roleName;
}
