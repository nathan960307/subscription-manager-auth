package com.project.subscription.auth.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 2000번대: 성공
    OK(2000, "성공"),
    CREATED(2010, "생성 완료"),
    ACCEPTED(2020, "요청 수락됨"),
    NO_CONTENT(2030, "내용 없음"),

    // 4000번대: 클라이언트 오류
    BAD_REQUEST(4000, "잘못된 요청"),
    UNAUTHORIZED(4010, "권한 없음"),
    FORBIDDEN(4030, "인증 실패"),
    NOT_FOUND(4040, "리소스를 찾을 수 없음"),

    // 5000번대: 서버 오류
    INTERNAL_SERVER_ERROR(5000, "서버 내부 오류"),      // 500 Internal Server Error
    NOT_IMPLEMENTED(5010, "구현되지 않음"),           // 501 Not Implemented
    BAD_GATEWAY(5020, "잘못된 게이트웨이"),          // 502 Bad Gateway
    SERVICE_UNAVAILABLE(5030, "서비스 사용 불가"),    // 503 Service Unavailable
    GATEWAY_TIMEOUT(5040, "게이트웨이 시간 초과");   // 504 Gateway Timeout

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
