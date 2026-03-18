package com.project.subscription.auth.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int status;

    // ErrorCode로 상태와 메시지 받음
    public CustomException(ErrorCode code) {
        super(code.getMessage());
        this.status = code.getStatus();
    }
}
