package com.project.subscription.auth.application.auth;

import com.project.subscription.auth.domain.user.User;
import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
import com.project.subscription.auth.infrastructure.redis.RedisService;
import com.project.subscription.auth.presentation.auth.dto.internal.RefreshInternalDto;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import com.project.subscription.auth.presentation.auth.dto.request.SigninRequest;
import com.project.subscription.auth.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    // 로그인(인증 필터 타지 않음)
    public SigninInternalDto login(SigninRequest request) {

        // 이메일로 사용자 조회
        User user = userRepository.findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다"));

        // 비밀번호 검증
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다");
        }

        // AccessToken 생성
        String accessToken = jwtProvider.createAccessToken(user.getId());

        // RefreshToken 생성
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        // RefreshToken 저장 (단일 로그인)
        redisService.save(
                "RT:" + user.getId(),
                refreshToken,
                jwtProvider.getRefreshTokenExpire()
        );
        
        // 내부 DTO 반환 ( 컨트롤러에서 Response 포장)
        SigninInternalDto signinInternalDTO = new SigninInternalDto(accessToken, refreshToken);

        return signinInternalDTO;
    }

    // 로그아웃(인증 필터 탐)
    public void logout(String accessToken, Long userId){

        // 1. RT 삭제
        redisService.delete("RT:" + userId);

        // 2. AT 블랙리스트 처리
        long remainingTime = jwtProvider.getRemainingTime(accessToken); // 남은 만료시간 계산

        redisService.save(
                "BL:" + accessToken,
                "logout",
                remainingTime
        );

    }

    // 토큰 재발급(인증 필터 타지 않음)
    public RefreshInternalDto refresh(String refreshToken){

        // 1. rt 검증
        jwtProvider.validateToken(refreshToken);

        // 2. userId 추출
        Long userId = jwtProvider.getUserIdFromToken(refreshToken);

        // 3. 저장된 rt 비교
        String savedRefreshToken = redisService.get("RT:" + userId);

        if(savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        // 4. 새 at 발급
        String newAccessToken = jwtProvider.createAccessToken(userId);

        // 5. 새 rt 발급 (rt rotation)
        String newRefreshToken = jwtProvider.createRefreshToken(userId);

        // 6. 새 rt redis 저장(기존 rt 덮어쓰기)
        redisService.save(
                "RT:" + userId,
                newRefreshToken,
                jwtProvider.getRefreshTokenExpire()
        );

        // 7. 내부 DTO 반환
        RefreshInternalDto refreshInternalDTO = new RefreshInternalDto(newAccessToken, newRefreshToken);

        return refreshInternalDTO;
    }

}
