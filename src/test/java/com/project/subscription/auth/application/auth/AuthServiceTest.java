package com.project.subscription.auth.application.auth;

import com.project.subscription.auth.domain.user.Role;
import com.project.subscription.auth.domain.user.User;
import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
import com.project.subscription.auth.infrastructure.redis.RedisService;
import com.project.subscription.auth.presentation.auth.dto.internal.RefreshInternalDto;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import com.project.subscription.auth.presentation.auth.dto.request.RefreshRequest;
import com.project.subscription.auth.presentation.auth.dto.request.SigninRequest;
import com.project.subscription.auth.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
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

    // 로그아웃 성공
    @Test
    void logout_success() {

        // given
        String token = "Bearer AT";

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader("Authorization")).thenReturn(token);

        when(jwtProvider.getRemainingTime(any()))
                .thenReturn(1000L);

        when(redisService.exists(any()))
                .thenReturn(false);

        // when
        authService.logout(request, 1L);

        // then
        verify(redisService).delete("RT:1");

        verify(redisService).save(
                startsWith("BL:"),
                eq("logout"),
                eq(1000L)
        );
    }

    // refresh 성공
    @Test
    void refresh_success() {

        // given - data setup
        Long userId = 1L;
        String refreshToken = "rt";
        String newAccessToken = "new-at";

        RefreshRequest request = new RefreshRequest(refreshToken);

        // given - mock setup

        User user = mock(User.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(user.getRole())
                .thenReturn(Role.USER);

        when(redisService.acquireLock(anyString(), anyLong()))
                .thenReturn(true);

        when(jwtProvider.getUserIdFromToken(refreshToken)) // RT → userId 추출
                .thenReturn(userId);

        when(jwtProvider.validateToken(refreshToken)) // RT 유효성 검사
                .thenReturn(true);

        when(redisService.get("RT:" + userId)) // Redis에 저장된 RT 조회
                .thenReturn(refreshToken);

        when(jwtProvider.createAccessToken(eq(userId), anyString())) // 새로운 AT 생성
                .thenReturn(newAccessToken);

        // when(실행)
        RefreshInternalDto result = authService.refresh(request.getRefreshToken());

        // then (검증)
        assertNotNull(result);
        assertEquals(newAccessToken, result.getAccessToken());
    }

    // refresh 실패 - rt 불일치
    @Test
    void refresh_fail_invalid_rt() {

        // given
        Long userId = 1L;
        String refreshToken = "rt";
        String savedRt = "different-rt";

        RefreshRequest request = new RefreshRequest(refreshToken);

        User user = mock(User.class);

        // mock
        when(redisService.acquireLock(anyString(), anyLong()))
                .thenReturn(true);

        when(jwtProvider.validateToken(refreshToken))
                .thenReturn(true);

        when(jwtProvider.getUserIdFromToken(refreshToken))
                .thenReturn(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(redisService.get("RT:" + userId))
                .thenReturn(savedRt);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            authService.refresh(request.getRefreshToken());
        });
    }

    // refresh 실패 - rt 미존재
    @Test
    void refresh_fail_no_rt() {

        // given
        Long userId = 1L;
        String refreshToken = "rt";

        RefreshRequest request = new RefreshRequest(refreshToken);

        User user = mock(User.class);

        // mock
        when(redisService.acquireLock(anyString(), anyLong()))
                .thenReturn(true);

        when(jwtProvider.validateToken(refreshToken))
                .thenReturn(true);

        when(jwtProvider.getUserIdFromToken(refreshToken))
                .thenReturn(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(redisService.get("RT:" + userId))
                .thenReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            authService.refresh(request.getRefreshToken());
        });
    }



}
