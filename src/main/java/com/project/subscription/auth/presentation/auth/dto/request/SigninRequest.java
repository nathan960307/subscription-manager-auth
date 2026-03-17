package com.project.subscription.auth.presentation.auth.dto.request;

import lombok.Getter;

@Getter
public class SigninRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
