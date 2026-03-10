package com.project.subscription.auth.presentation.auth.controller;

import com.project.subscription.auth.application.auth.AuthService;
import com.project.subscription.auth.presentation.auth.dto.request.SigninRequest;
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
    public void login(@RequestBody SigninRequest signinRequest) {
        authService.login();
    }

    // 로그아웃
    @PostMapping("/logout")
    public void logout() {

    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public void reissue() {

    }
}
