package com.project.subscription.auth.presentation.user.controller;

import com.project.subscription.auth.application.user.UserService;
import com.project.subscription.auth.global.response.ApiResponse;
import com.project.subscription.auth.presentation.user.dto.internal.AdminUserInternalDto;
import com.project.subscription.auth.presentation.user.dto.internal.UserInternalDto;
import com.project.subscription.auth.presentation.user.dto.request.SignupRequest;
import com.project.subscription.auth.presentation.user.dto.request.UpdateUserRequest;
import com.project.subscription.auth.presentation.user.dto.response.AdminResponse;
import com.project.subscription.auth.presentation.user.dto.response.MyInfoResponse;
import com.project.subscription.auth.presentation.user.dto.response.SignupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<?> signup(@Valid @RequestBody SignupRequest signupRequest) {

        userService.signup(signupRequest);

        return ApiResponse.success(null);
    }


    // 내 정보 조회
    // complete
    @GetMapping("/me")
    public ApiResponse<?> getMyInfo(@AuthenticationPrincipal Long userId) {

        UserInternalDto userInternalDto = userService.getMyInfo(userId);

        return ApiResponse.success(userInternalDto);
    }

    // 정보수정
    // complete
    @PatchMapping("/me")
    public ApiResponse<?> updateMyInfo(@AuthenticationPrincipal Long userId,
                             @RequestBody UpdateUserRequest updateUserRequest) {

        UserInternalDto userInternalDto = userService.updateMyInfo(userId,updateUserRequest);

        return ApiResponse.success(userInternalDto);
    }

    // 회원탈퇴
    // complete
    @DeleteMapping("/me")
    public ApiResponse<?> deleteMyInfo(@AuthenticationPrincipal Long userId) {

        userService.deleteMyInfo(userId);

        return ApiResponse.success(null);
    }

    /// ===========================
    /// 👤 관리자 영역
    /// ===========================

    // 전체 사용자 조회
    // complete
    @GetMapping
    public ApiResponse<?> getAllUsers(){

        List<AdminUserInternalDto> adminUserInternalDto = userService.getAllUsers();

        return ApiResponse.success(adminUserInternalDto);

    }

    // 특정 사용자 조회
    // complete
    @GetMapping("/{id}")
    public ApiResponse<?> getUser(@PathVariable Long id){

        AdminUserInternalDto adminUserInternalDto = userService.getUser(id);

        return ApiResponse.success(adminUserInternalDto);

    }

    // 특정 사용자 삭제
    // complete
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(@PathVariable Long id){

        userService.deleteUser(id);

        return ApiResponse.success(null);
    }

}
