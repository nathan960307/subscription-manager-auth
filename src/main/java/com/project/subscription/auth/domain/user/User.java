package com.project.subscription.auth.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @Column(name = "name")
    private String name; // 사용자 이름

    @Column(name = "phone_number")
    private String phoneNumber; // 사용자 전화번호

    @Column(name = "provider")
    private String provider; // 로그인 제공자

    @Column(name = "provider_id")
    private String providerId; // OAuth 고유 아이디

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt; // 최근 로그인 날짜

    @Column(name = "role", nullable = false)
    private String role; // 역할

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false; // 삭제 여부

    @Column(name = "is_blacklisted", nullable = false)
    private boolean blacklisted = false; // 블랙리스트 여부

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 일자

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 일자

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 삭제 일자

    // 도메인 메서드

    // 유저 생성
    public static User createUser(String email, String password) {

        User user = new User();
        user.email = email;
        user.password = password;
        user.role = "USER";   // 기본값

        return user;
    }

    // 유저 삭제
    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }


}
