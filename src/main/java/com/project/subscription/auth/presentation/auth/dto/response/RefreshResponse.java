package com.project.subscription.auth.presentation.auth.dto.response;

import com.project.subscription.auth.presentation.auth.dto.internal.RefreshInternalDto;
import com.project.subscription.auth.presentation.auth.dto.internal.SigninInternalDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class RefreshResponse {

    private final String message;
    private final OffsetDateTime timestamp;
    private final RefreshInternalDto data;

    public static RefreshResponse success(RefreshInternalDto refreshInternalDto) {

        return new RefreshResponse(
                "재발급 성공",
                OffsetDateTime.now(),
                refreshInternalDto
        );
    }
}
