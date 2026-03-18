package com.project.subscription.auth.application.auth;

import com.project.subscription.auth.domain.user.User;
import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
import com.project.subscription.auth.infrastructure.redis.RedisService;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import com.project.subscription.auth.presentation.auth.dto.request.SigninRequest;
import com.project.subscription.auth.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    // mock 객체 선언
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private RedisService redisService;

    // test 대상 주입
    @InjectMocks
    private AuthService authService;

    // 테스트 메서드

    // 로그인 성공
    @Test
    void login_success() {

        // given - data setup
        SigninRequest request = new SigninRequest("test@test.com", "1234");
        User user = User.createUser(
                "test@test.com",
                "encodedPassword",
                "홍길동",
                "01012345678"
        );

        ReflectionTestUtils.setField(user, "id", 1L); // id 강제 삽입

        // given - mock setup
        when(userRepository.findByEmailAndDeletedFalse(any()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);

        when(jwtProvider.createAccessToken(any(), any()))
                .thenReturn("AT");

        when(jwtProvider.createRefreshToken(any()))
                .thenReturn("RT");

        when(jwtProvider.getRefreshTokenExpire())
                .thenReturn(1000L);

        // when (실행)
        SigninInternalDto result = authService.login(request);

        // then (검증)
        assertNotNull(result);

        verify(redisService).save(
                "RT:" + user.getId(),
                "RT",
                1000L
        );


    }

    // 로그인 실패
    @Test
    void login_fail() {

        // given - data setup
        SigninRequest request = new SigninRequest("test@test.com", "1234");
        User user = User.createUser(
                "test@test.com",
                "encodedPassword",
                "홍길동",
                "01012345678"
        );

        ReflectionTestUtils.setField(user, "id", 1L); // id 강제 삽입

        // given - mock setup
        when(userRepository.findByEmailAndDeletedFalse(any()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
                .thenReturn(false);


        // then (검증)
        assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });


    }


}
