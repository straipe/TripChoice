package com.ssafy12.tripchoice.tripplan.comment.exception;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp404Exception;

public class CommentNotFoundException extends CustomHttp404Exception {
    public CommentNotFoundException() {
        this("Can't find the comment");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
