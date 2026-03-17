package com.project.subscription.auth.presentation.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class SignupResponse {

    private final String message;
    private final OffsetDateTime timestamp;


    public static SignupResponse success() {

        return new SignupResponse(
                "회원가입 성공",
                OffsetDateTime.now()
        );
    }
}
