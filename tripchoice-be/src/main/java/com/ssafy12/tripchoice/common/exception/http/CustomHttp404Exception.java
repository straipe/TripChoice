package com.ssafy12.tripchoice.common.exception.http;

import com.ssafy12.tripchoice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CustomHttp404Exception extends CustomException {
    private static final String MESSAGE = "NotFound: can't find the requested resource";
    public CustomHttp404Exception() {
        super(MESSAGE);
    }

    public CustomHttp404Exception(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
