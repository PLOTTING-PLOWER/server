package com.plotting.server.global.filter;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.exception.errorcode.ErrorCode;
import com.plotting.server.global.util.JwtUtil;
import com.plotting.server.user.application.UserDetailsServiceImpl;
import com.plotting.server.user.exception.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken(request);   // request 헤더에서 토큰 추출

        if(token != null && jwtUtil.validateToken(token)) {
            try {
                Long userId = jwtUtil.getIdFromToken(token);
                JwtUserDetails userDetails = userDetailsService.loadUserByUserId(userId);
                log.info("Extracted userId from JWT: {}", userId);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(userDetails.role().name())));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    log.error("Invalid userId extracted from token.");
                    throw new UserNotFoundException(USER_NOT_FOUND);
                }
            }catch (Exception ex){
                log.error("Authentication failed: ", ex.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }
}
