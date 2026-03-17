package com.project.subscription.auth.presentation.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest{

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
}
