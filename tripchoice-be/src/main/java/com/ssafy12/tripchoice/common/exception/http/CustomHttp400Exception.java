package com.ssafy12.tripchoice.common.exception.http;

import com.ssafy12.tripchoice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CustomHttp400Exception extends CustomException {
    private static final String MESSAGE = "Bad Request";
    public CustomHttp400Exception() {
        super(MESSAGE);
    }

    public CustomHttp400Exception(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
