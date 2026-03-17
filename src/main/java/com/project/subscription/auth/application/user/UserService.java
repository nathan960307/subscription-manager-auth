package com.project.subscription.auth.application.user;

import com.project.subscription.auth.domain.user.User;
import com.project.subscription.auth.presentation.user.dto.internal.AdminUserInternalDto;
import com.project.subscription.auth.presentation.user.dto.internal.UserInternalDto;
import com.project.subscription.auth.presentation.user.dto.request.SignupRequest;
import com.project.subscription.auth.presentation.user.dto.request.UpdateUserRequest;
import com.project.subscription.auth.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    // complete
    @Transactional
    public void signup(SignupRequest request) {

        // 이메일 중복 검사
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // user 생성(도메인 메서드)
        User user = User.createUser(request.getEmail(),encodedPassword,request.getName(),request.getPhoneNumber());

        // 저장
        userRepository.save(user);
    }

    // 내 정보 조회
    // complete
    @Transactional(readOnly = true)
    public UserInternalDto getMyInfo(Long userId) {

        // 유저 조회
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // User entity -> DTO 변환
        UserInternalDto userInternalDto = UserInternalDto.from(user);

        //반환
        return userInternalDto;
    }

    // 내 정보 수정
    // complete
    @Transactional
    public UserInternalDto updateMyInfo(Long userId, UpdateUserRequest request) {

        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateUser(request.getName(), request.getPhoneNumber());

        UserInternalDto userInternalDto = UserInternalDto.from(user);

        return userInternalDto;
    }

    // 내 정보 삭제(탈퇴)
    // complete
    @Transactional
    public void deleteMyInfo(Long userId){

        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.delete(); // deleted=true, deletedAt=now()
    }

    // 모든 사용자 조회
    @Transactional(readOnly = true)
    public List<AdminUserInternalDto> getAllUsers(){

        // 사용자 목록 조회
        List<User> users = userRepository.findAllByDeletedFalse();

        // 사용자 entity -> dto로 변환
        List<AdminUserInternalDto> adminUserInternalDtos = users.stream()
                .map(AdminUserInternalDto::from)
                .toList();

        // dto 목록 반환
        return adminUserInternalDtos;

    }

    // 특정 사용자 조회
    @Transactional(readOnly = true)
    public AdminUserInternalDto getUser(Long userId){

        // 사용자 조회
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AdminUserInternalDto adminUserInternalDto = AdminUserInternalDto.from(user);

        return adminUserInternalDto;
    }

    // 특정 사용자 삭제
    @Transactional
    public void deleteUser(Long userId){

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.delete();
    }
}
