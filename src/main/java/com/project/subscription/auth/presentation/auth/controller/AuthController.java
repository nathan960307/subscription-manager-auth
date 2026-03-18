package com.project.subscription.auth.presentation.auth.controller;

import com.project.subscription.auth.application.auth.AuthService;
import com.project.subscription.auth.global.response.ApiResponse;
import com.project.subscription.auth.presentation.auth.dto.internal.RefreshInternalDto;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import com.project.subscription.auth.presentation.auth.dto.request.RefreshRequest;
import com.project.subscription.auth.presentation.auth.dto.request.SigninRequest;
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
    public ApiResponse<?> login(@RequestBody SigninRequest signinRequest) {

        SigninInternalDto signinInternalDTO = authService.login(signinRequest);

        return ApiResponse.success(signinInternalDTO);
    }

    // 로그아웃
    // complete
    @PostMapping("/logout")
    public ApiResponse<?> logout(
            HttpServletRequest request,
            @AuthenticationPrincipal Long userId) {

        authService.logout(request,userId);

        return ApiResponse.success(null);
    }

    // 토큰 재발급
    // complete
    @PostMapping("/refresh")
    public ApiResponse<?> refresh(@RequestBody RefreshRequest refreshRequest) {

        String RefreshToken = refreshRequest.getRefreshToken();

        RefreshInternalDto refreshInternalDto = authService.refresh(RefreshToken);

        return ApiResponse.success(refreshInternalDto);
    }
}
