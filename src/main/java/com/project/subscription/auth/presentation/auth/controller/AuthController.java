package com.project.subscription.auth.presentation.auth.controller;

import com.project.subscription.auth.application.auth.AuthService;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import com.project.subscription.auth.presentation.auth.dto.request.SigninRequest;
import com.project.subscription.auth.presentation.auth.dto.response.SigninResponse;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/login")
    public SigninResponse login(@RequestBody SigninRequest signinRequest) {

        SigninInternalDto signinInternalDTO = authService.login(signinRequest);

        SigninResponse signinResponse = SigninResponse.success(signinInternalDTO);

        return signinResponse;

    }

    // 로그아웃
    @PostMapping("/logout")
    public void logout() {

    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public void refresh() {

    }
}
