package com.plotting.server.user.dto.request;

import com.plotting.server.comment.domain.Comment;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.LoginType;
import com.plotting.server.user.domain.UserType.Role;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
public record SignUpRequest (
     String nickname,
     String email,
     String password
){
    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .nickname(this.nickname)
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .socialId(null)     // 일반 회원 가입
                .profileImageUrl("")  // 기본은 앱 로고
                .profileMessage("안녕하세요! "+this.nickname + " 입니다.")
                .loginType(LoginType.SELF)
                .isAlarmAllowed(true)
                .isProfilePublic(true)
                .role(Role.USER)
                .fcmToken("")       //초기값 설정 필요..
                .build();
    }
}
