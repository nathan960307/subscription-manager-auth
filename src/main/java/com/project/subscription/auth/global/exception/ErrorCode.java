package com.project.subscription.auth.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다"),
    INVALID_PASSWORD(401, "비밀번호가 틀립니다");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
