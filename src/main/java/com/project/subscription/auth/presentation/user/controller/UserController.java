package com.project.subscription.auth.presentation.user.controller;

import com.project.subscription.auth.application.user.UserService;
import com.project.subscription.auth.presentation.user.dto.internal.UserInternalDto;
import com.project.subscription.auth.presentation.user.dto.request.SignupRequest;
import com.project.subscription.auth.presentation.user.dto.response.MyInfoResponse;
import com.project.subscription.auth.presentation.user.dto.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    // complete
    @PostMapping("/signup")
    public SignupResponse signup(@RequestBody SignupRequest signupRequest) {

        userService.signup(signupRequest);

        SignupResponse signinResponse = SignupResponse.success();

        return signinResponse;
    }


    // 내 정보 조회
    // complete
    @GetMapping("/me")
    public MyInfoResponse getMyInfo(@AuthenticationPrincipal Long userId) {

        UserInternalDto userInternalDto = userService.getMyInfo(userId);

        MyInfoResponse myInfoResponse = MyInfoResponse.success(userInternalDto);

        return myInfoResponse;
    }

    // 정보수정
    @PatchMapping("/me")
    public void updateMyInfo(){

    }

    // 회원탈퇴
    @DeleteMapping("/me")
    public MyInfoResponse deleteMyInfo(@AuthenticationPrincipal Long userId) {

        userService.deleteMyInfo(userId);

        MyInfoResponse myInfoResponse = MyInfoResponse.delete();

        return myInfoResponse;
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
