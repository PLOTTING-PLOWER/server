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
        String password,
        Role role
)implements UserDetails {
    public static JwtUserDetails from(User user){
        return JwtUserDetails.builder()
                .userId(user.getId())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static JwtUserDetails from(Claims claims){
        return JwtUserDetails.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(Role.valueOf(claims.get("role").toString()))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId); // userId를 username으로 활용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료되지 않음을 기본값으로 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금되지 않음을 기본값으로 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않음을 기본값으로 설정
    }

    @Override
    public boolean isEnabled() {
        return true; // 기본적으로 활성화된 상태로 설정
    }

}