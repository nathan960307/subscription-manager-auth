package com.project.subscription.auth.application.auth;

import com.project.subscription.auth.domain.user.User;
import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
import com.project.subscription.auth.presentation.auth.dto.internal.RefreshInternalDTO;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDTO;
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

    // 로그인(인증 필터 타지 않음)
    public SigninInternalDTO login(SigninRequest request) {

        // 1. 이메일로 사용자 조회 ( 없으면 예외 처리)
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException());

        // 2. 비밀번호 검증
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException();
        }

        // 3. JWT AccessToken 생성
        String accessToken = jwtProvider.createAccessToken(user.getId());

        // 4. RefreshToken 생성 및 저장
        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        // rt 저장 로직 추가 필요
        
        // 5. 내부 DTO 반환 ( 컨트롤러에서 Response 포장)
        SigninInternalDTO signinInternalDTO = new SigninInternalDTO(accessToken, refreshToken);

        return signinInternalDTO;
    }

    // 로그아웃(인증 필터 탐)
    public void logout(Long userId){

        // 1. RT 삭제

        // 2. AT 블랙리스트 처리
    }

    // 토큰 재발급(인증 필터 타지 않음)
    public RefreshInternalDTO refresh(String refreshToken){

        // 1. rt 검증
        jwtProvider.validateToken(refreshToken);

        // 2. userId 추출
        Long userId = jwtProvider.getUserIdFromToken(refreshToken);

        // 3. 저장된 rt 비교

        // 4. 새 at 발급
        String newAccessToken = jwtProvider.createAccessToken(userId);

        // 5. 새 rt 발급 (rt rotation)
        String newRefreshToken = jwtProvider.createRefreshToken(userId);

        // 6. 새 rt redis 저장(기존 rt 덮어쓰기)

        // 7. 내부 DTO 반환
        RefreshInternalDTO  refreshInternalDTO = new RefreshInternalDTO(newAccessToken, newRefreshToken);

        return refreshInternalDTO;
    }

}
