package com.plotting.server.global.util;

import com.plotting.server.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    // secretKey를 외부에서 접근 가능하도록 getter 추가
    @Getter
    private final String secretKey; // secretKey 저장
    private final Key key;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;    // 2시간
    private final String ISSUER = "plotting";

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey; // secretKey를 필드에 저장
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user) {    //사용자 정보를 포함하는 JWT 토큰을 생성
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("nickname", user.getNickname())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setIssuer(ISSUER)
                .signWith(key)
                .compact();
    }

    // JWT 토큰 검증 메서드
    public boolean validateToken(String token) {    // 토큰 유효성 검증
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e) {
            log.error("Invalid JWT token. Reason: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Token is null or empty. Reason: {}", e.getMessage());
        }
        return false;
    }

    public String getIdFromToken(String token) {     // 토큰에서 이메일 (사용자 식별자) 추출
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


}
