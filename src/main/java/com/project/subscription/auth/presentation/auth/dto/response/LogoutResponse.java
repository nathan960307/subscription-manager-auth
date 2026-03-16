package com.project.subscription.auth.presentation.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class LogoutResponse {

    private final String message;
    private final OffsetDateTime timestamp;


    public static LogoutResponse success() {

        return new LogoutResponse(
                "로그아웃 성공",
                OffsetDateTime.now()
        );
    }
}
