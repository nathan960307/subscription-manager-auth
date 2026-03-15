package com.project.subscription.auth.presentation.auth.dto.response;

import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class SigninResponse {

    private final String message;
    private final OffsetDateTime timestamp;
    private final SigninInternalDto data;

    public static SigninResponse success(SigninInternalDto signinInternalDto) {

        return new SigninResponse(
                "로그인 성공",
                OffsetDateTime.now(),
                signinInternalDto
        );
    }
}
