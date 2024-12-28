package com.ssafy12.tripchoice.common.exception.http;

import com.ssafy12.tripchoice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CustomHttp409Exception extends CustomException {
    private static final String MESSAGE = "Conflict: the resource is already exists";
    public CustomHttp409Exception() {
        super(MESSAGE);
    }

    public CustomHttp409Exception(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
