package com.project.subscription.auth.presentation.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshRequest {

    @NotBlank
    private String refreshToken;
}
