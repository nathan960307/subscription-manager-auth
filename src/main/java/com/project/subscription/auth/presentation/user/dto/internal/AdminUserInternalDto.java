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
    String role;
    boolean deleted;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;


    // 정적 팩토리 메서드
    public static AdminUserInternalDto from(User user) {
        return  AdminUserInternalDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .deleted(user.isDeleted())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .deletedAt(user.getDeletedAt())
                    .build();
    }

}
