package com.ssafy12.tripchoice.common.exception.http;

import com.ssafy12.tripchoice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CustomHttp403Exception extends CustomException {
    private static final String MESSAGE = "Forbidden: requested resource needs authority";
    public CustomHttp403Exception() {
        super(MESSAGE);
    }

    public CustomHttp403Exception(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
