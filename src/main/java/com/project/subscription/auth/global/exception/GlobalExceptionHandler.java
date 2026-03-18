package com.project.subscription.auth.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 컨트롤러에서 처리되지 않은 RuntimeException을 잡아서
    // 400 Bad Request와 메시지를 반환
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handle(RuntimeException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
