package com.plotting.server.global.dto;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import io.jsonwebtoken.Claims;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Builder
public record JwtUserDetails(
        Long userId,
        Role role
) {
    public static JwtUserDetails from(User user){
        return JwtUserDetails.builder()
                .userId(user.getId())
                .role(user.getRole())
                .build();
    }

    public static JwtUserDetails from(Claims claims){
        return JwtUserDetails.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(Role.valueOf(claims.get("role").toString()))
                .build();
    }


}
