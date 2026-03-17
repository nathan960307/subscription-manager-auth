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

    // 로그인 (인증 필터 타지 않음)
    // complete
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

        // 로그인 로그 관리를 위한 DB 저장 - 필요시 추가
        
        // 내부 DTO 반환 ( 컨트롤러에서 Response 포장)
        SigninInternalDto signinInternalDTO = new SigninInternalDto(accessToken, refreshToken);

        return signinInternalDTO;
    }

    // 로그아웃 (인증 필터 탐)
    // complete
    public void logout(String accessToken, Long userId){

        // RT 삭제
        redisService.delete("RT:" + userId);

        // AT 블랙리스트 처리
        long remainingTime = jwtProvider.getRemainingTime(accessToken); // 남은 만료시간 계산

        redisService.save(
                "BL:" + accessToken,
                "logout",
                remainingTime
        );

    }

    // 토큰 재발급 (인증 필터 타지 않음)
    public RefreshInternalDto refresh(String refreshToken){

        // redis 락 사용
        String lockKey = "LOCK:RT:" + refreshToken; // redis 락 키
        long lockTTL = 5000; // TTL 시간 : 5초
        boolean acquired = redisService.acquireLock(lockKey, lockTTL); // 키 존재 여부

        if(!acquired){ // 키 기존재
            throw new RuntimeException("동시 재발급 시도 중");
        }

        try{
        // rt 검증
        jwtProvider.validateToken(refreshToken);

        // userId 추출
        Long userId = jwtProvider.getUserIdFromToken(refreshToken);

        // 저장된 RT 조회
        String savedRefreshToken = redisService.get("RT:" + userId);

        // 저장된 rt 비교
        if(savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        // 새 at 발급
        String newAccessToken = jwtProvider.createAccessToken(userId);

        // 새 rt 발급 (rt rotation)
        String newRefreshToken = jwtProvider.createRefreshToken(userId);

        // 새 rt redis 저장(기존 rt 덮어쓰기)
        redisService.save(
                "RT:" + userId,
                newRefreshToken,
                jwtProvider.getRefreshTokenExpire()
        );

        // 내부 DTO 반환
        RefreshInternalDto refreshInternalDTO = new RefreshInternalDto(newAccessToken, newRefreshToken);

        return refreshInternalDTO;

        }finally {
            // redis 락 해제
            redisService.releaseLock(lockKey);
        }


    }

}
