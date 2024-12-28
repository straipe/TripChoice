package com.ssafy12.tripchoice.auth.exception;

import com.ssafy12.tripchoice.auth.config.AuthProps;
import com.ssafy12.tripchoice.common.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.joinWith;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionController {

    private final AuthProps authProps;

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthenticatedException(UnauthenticatedException e) {
        log.warn("인증 예외 발생 = {}",e.toString());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, joinWith(SPACE, authProps.scheme, "realm=\"access to the api\""))
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        log.warn("인가 예외 발생 = {}",e.toString());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }
}
