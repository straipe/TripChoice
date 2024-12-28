package com.ssafy12.tripchoice.common.exception.http;

import com.ssafy12.tripchoice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CustomHttp401Exception extends CustomException {
    private static final String MESSAGE = "Unauthorized: the request needs authentication";
    public CustomHttp401Exception() {
        super(MESSAGE);
    }

    public CustomHttp401Exception(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
