package com.project.subscription.auth.presentation.user.dto.response;

import com.project.subscription.auth.presentation.user.dto.internal.UserInternalDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class MyInfoResponse {

    private final String message;
    private final OffsetDateTime timestamp;
    private final UserInternalDto data;


    public static MyInfoResponse success(UserInternalDto userInternalDto) {

        return new MyInfoResponse(
                "내 정보 조회 성공",
                OffsetDateTime.now(),
                userInternalDto
        );
    }

    public static MyInfoResponse delete() {
        return new MyInfoResponse(
                "회원 탈퇴 성공",
                OffsetDateTime.now(),
                null
        );
    }

    public static MyInfoResponse update(UserInternalDto userInternalDto) {
        return new MyInfoResponse(
                "회원 수정 성공",
                OffsetDateTime.now(),
                null
        );
    }
}
