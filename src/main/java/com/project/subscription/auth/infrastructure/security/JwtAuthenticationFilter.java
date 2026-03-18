package com.project.subscription.auth.infrastructure.security;

import com.project.subscription.auth.global.exception.CustomException;
import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
import com.project.subscription.auth.infrastructure.redis.RedisService;
import jakarta.servlet.FilterChain;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authoriaztion 헤더에서 토큰 꺼내기
        String bearer =  request.getHeader("Authorization");


        // 토큰 검증
        if(bearer != null && bearer.startsWith("Bearer ")){
            String token = bearer.substring(7);

            // 블랙리스트 AT 검증
            if (redisService.get("BL:" + token) != null) {
                // AuthExceptionFilter 만들어서 예외처리 해야하던 아래와 같은 방법으로 상태 내리고 끝내야함
                 response.setStatus(401);
                 response.getWriter().write("로그아웃된 토큰");
                 return;
            }

            try {
                if (jwtProvider.validateToken(token)) {

                    Long userId = jwtProvider.getUserIdFromToken(token); // 토큰에서 userId 조회
                    String role = jwtProvider.getRoleFromToken(token); // 토큰에서 role 조회

                    // 인증 객체 생성
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );

                    // 인증 객체 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (CustomException e){
                response.setStatus(401);
                response.getWriter().write("유효하지 않은 토큰");
                return;

            }
        }

        filterChain.doFilter(request, response);
    }



}
