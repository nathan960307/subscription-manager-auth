package com.project.subscription.auth.presentation.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest{

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String name;
    private String phoneNumber;
}
