package com.project.subscription.auth.application.auth;

import com.project.subscription.auth.domain.user.User;
import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
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

    // 로그인
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

    // 로그아웃
    public void logout(){

    }

    // 토큰 재발급
    public void reissue(){

    }

}
