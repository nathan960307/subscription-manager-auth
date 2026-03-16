package com.project.subscription.auth.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "users")
public class User {

    // 필드

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user ID

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 사용자 이메일

    @Column(name = "password", nullable = false)
    private String password; // 사용자 비밀번호

    @Column(name = "role", nullable = false)
    private String role; // 역할

    // 도메인 메서드
    public static User createUser(String email, String password) {

        User user = new User();
        user.email = email;
        user.password = password;
        user.role = "USER";   // 기본값

        return user;
    }


}
