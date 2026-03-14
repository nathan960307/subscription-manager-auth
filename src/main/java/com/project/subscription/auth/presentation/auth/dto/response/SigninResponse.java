package com.project.subscription.auth.presentation.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninResponse {

    private String access_token;
    private String refresh_token;
}
