package com.project.subscription.auth.presentation.auth.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshInternalDTO {

    private String accessToken;
    private String refreshToken;

}
