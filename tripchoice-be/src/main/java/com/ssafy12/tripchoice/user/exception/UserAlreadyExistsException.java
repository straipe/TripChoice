package com.ssafy12.tripchoice.user.exception;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp409Exception;

public class UserAlreadyExistsException extends CustomHttp409Exception {
    public UserAlreadyExistsException() {
        this("The user is already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
