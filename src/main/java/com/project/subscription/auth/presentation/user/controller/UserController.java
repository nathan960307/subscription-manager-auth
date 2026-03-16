package com.project.subscription.auth.presentation.user.controller;

import com.project.subscription.auth.application.user.UserService;
import com.project.subscription.auth.presentation.user.dto.request.SignupRequest;
import com.project.subscription.auth.presentation.user.dto.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /// ===========================
    /// 👤 일반 사용자 영역
    /// ===========================

    // 회원가입
    @PostMapping("/signup")
    public SignupResponse signup(@RequestBody SignupRequest signupRequest) {

        userService.signup(signupRequest);

        SignupResponse signinResponse = SignupResponse.success();

        return signinResponse;
    }


    // 내 정보 조회
    @GetMapping("/me")
    public void getMyInfo(){

    }

    // 정보수정
    @PatchMapping("/me")
    public void updateMyInfo(){

    }

    // 회원탈퇴
    @DeleteMapping("/me")
    public void deleteMyInfo(){

    }

    /// ===========================
    /// 👤 관리자 영역
    /// ===========================

    // 전체 사용자 조회
    @GetMapping
    public void getAllUsers(){

    }

    // 특정 사용자 조회
    @GetMapping("/{id}")
    public void getUser(@PathVariable Long id){

    }

    // 특정 사용자 삭제
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){

    }

}
