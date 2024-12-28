package com.ssafy12.tripchoice.auth.exception;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp403Exception;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomHttp403Exception {
    public static final String DEFAULT_MESSAGE = "해당 작업에 대한 권한이 없습니다.";
    public UnauthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
