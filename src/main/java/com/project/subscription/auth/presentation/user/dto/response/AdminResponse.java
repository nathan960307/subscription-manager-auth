package com.project.subscription.auth.presentation.user.dto.response;

import com.project.subscription.auth.presentation.user.dto.internal.AdminUserInternalDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AdminResponse<T> {

    private final String message;
    private final OffsetDateTime timestamp;
    private final T data;


    public static AdminResponse<List<AdminUserInternalDto>> getAllUsers(List<AdminUserInternalDto> users) {

        return new AdminResponse<>(
                "모든 사용자 조회 성공",
                OffsetDateTime.now(),
                users
        );
    }

    public static AdminResponse<AdminUserInternalDto> getUser(AdminUserInternalDto user) {

        return new AdminResponse<>(
                "단일 사용자 조회 성공",
                OffsetDateTime.now(),
                user
        );
    }


}
