package com.project.subscription.auth.presentation.auth.dto.request;

import lombok.Getter;

@Getter
public class SigninRequest {

    private String email;
    private String password;
}
