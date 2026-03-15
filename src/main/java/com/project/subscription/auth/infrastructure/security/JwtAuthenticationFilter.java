package com.project.subscription.auth.infrastructure.security;

import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Authoriaztion 헤더에서 토큰 꺼내기
        String bearer =  request.getHeader("Authorization");

        // 2. 토큰 검증
        if(bearer != null && bearer.startsWith("Bearer ")){
            String token = bearer.substring(7);

            if(jwtProvider.validateToken(token)){

                Long userId = jwtProvider.getUserIdFromToken(token);

                // 인증 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

                // 인증 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }



}
