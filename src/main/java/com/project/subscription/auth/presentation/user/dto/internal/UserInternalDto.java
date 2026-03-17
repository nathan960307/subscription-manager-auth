package com.project.subscription.auth.presentation.user.dto.internal;

import com.project.subscription.auth.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInternalDto {

    // 필드
    String email;
    String role;
    String name;
    String phoneNumber;

    // 정적 팩토리 메서드
    public static UserInternalDto from(User user) {
        return  UserInternalDto.builder()
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
    }

}
