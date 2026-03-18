package com.project.subscription.auth.application.user;

import com.project.subscription.auth.domain.user.User;
import com.project.subscription.auth.presentation.user.dto.request.SignupRequest;
import com.project.subscription.auth.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // mock 객체 선언
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // 테스트 메서드

    // 회원가입 성공
    @Test
    void signup_success() {

        // given
        SignupRequest request = new SignupRequest(
                "test@test.com",
                "1234",
                "홍길동",
                "01012345678"
        );

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(any()))
                .thenReturn("encoded");

        // when
        userService.signup(request);

        // then
        verify(userRepository).save(any(User.class));
    }

    // 회원가입 실패

    // 내 정보 삭제(탈퇴)



}
