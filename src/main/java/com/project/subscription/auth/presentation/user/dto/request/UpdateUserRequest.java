package com.project.subscription.auth.presentation.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {


    private String name;
    private String phoneNumber;
}
