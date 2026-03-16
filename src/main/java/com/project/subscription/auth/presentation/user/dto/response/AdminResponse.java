package com.project.subscription.auth.presentation.user.dto.response;

import com.project.subscription.auth.presentation.user.dto.internal.AdminUserInternalDto;
import com.project.subscription.auth.presentation.user.dto.internal.UserInternalDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AdminResponse {

    private final String message;
    private final OffsetDateTime timestamp;
    private final List<AdminUserInternalDto> data;


    public static AdminResponse getAllUsers(List<AdminUserInternalDto> users) {

        return new AdminResponse(
                "모든 사용자 조회 성공",
                OffsetDateTime.now(),
                users
        );
    }


}
