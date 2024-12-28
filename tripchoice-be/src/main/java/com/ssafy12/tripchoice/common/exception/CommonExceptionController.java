package com.ssafy12.tripchoice.common.exception;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp404Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionController {

    @ExceptionHandler({
            BindException.class,
            IllegalStateException.class,
            IllegalArgumentException.class,
            MissingRequestCookieException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception e) {
        log.info("bad request handle : {}",e.getMessage());
        if (e instanceof BindException be) {
            FieldError error = be.getFieldError();
            return ResponseEntity.status(BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .message(error.getField() + " " + error.getDefaultMessage())
                            .build());
        }
        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler({CustomHttp404Exception.class})
    public ResponseEntity<ErrorResponse> handleNotFound(CustomHttp404Exception e) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }
}
