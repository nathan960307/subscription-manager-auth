package com.project.subscription.auth.presentation.auth.controller;

import com.project.subscription.auth.application.auth.AuthService;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import com.project.subscription.auth.presentation.auth.dto.request.SigninRequest;
import com.project.subscription.auth.presentation.auth.dto.response.SigninResponse;
import com.project.subscription.auth.presentation.user.dto.response.LogoutResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    // 로그인
    // complete
    @PostMapping("/login")
    public SigninResponse login(@RequestBody SigninRequest signinRequest) {

        SigninInternalDto signinInternalDTO = authService.login(signinRequest);

        SigninResponse signinResponse = SigninResponse.success(signinInternalDTO);

        return signinResponse;

    }

    // 로그아웃
    // complete
    @PostMapping("/logout")
    public LogoutResponse logout(
            HttpServletRequest request,
            @AuthenticationPrincipal Long userId) {

        String bearerToken = request.getHeader("Authorization");
        String accessToken = bearerToken.substring(7);

        authService.logout(accessToken,userId);

        LogoutResponse logoutResponse = LogoutResponse.success();

        return logoutResponse;
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public void refresh() {

    }
}
