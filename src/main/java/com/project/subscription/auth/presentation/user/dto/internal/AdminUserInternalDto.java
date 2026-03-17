package com.project.subscription.auth.presentation.user.dto.internal;

import com.project.subscription.auth.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 관리자용 유저 dto

@Getter
@Builder
@AllArgsConstructor
public class AdminUserInternalDto {

    // 필드
    Long id;
    String email;
    String name;
    String phoneNumber;
    String role;
    String provider;
    boolean deleted;
    boolean blacklisted;
    LocalDateTime lastLoginAt;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;


    // 정적 팩토리 메서드
    public static AdminUserInternalDto from(User user) {
        return  AdminUserInternalDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .provider(user.getProvider().name())
                .deleted(user.isDeleted())
                .blacklisted(user.isBlacklisted())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }

}
