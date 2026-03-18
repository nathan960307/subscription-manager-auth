package com.project.subscription.auth.application.auth;

import com.project.subscription.auth.infrastructure.jwt.JwtProvider;
import com.project.subscription.auth.infrastructure.redis.RedisService;
import com.project.subscription.auth.repository.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private AuthService authService;
}
