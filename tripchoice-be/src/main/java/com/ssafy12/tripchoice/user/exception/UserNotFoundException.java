package com.ssafy12.tripchoice.user.exception;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp404Exception;

public class UserNotFoundException extends CustomHttp404Exception {
    public UserNotFoundException() {
        this("Can't find the user");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
