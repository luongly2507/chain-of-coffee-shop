package com.app.coffee.advice;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.app.coffee.exception.RefreshTokenException;
import com.app.coffee.payload.response.ErrorMessageResponse;

@RestControllerAdvice
public class RefreshTokenExceptionHandler {
    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleTokenRefreshException(RefreshTokenException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorMessageResponse.builder()
                        .statusCode(403)
                        .message(ex.getMessage())
                        .timeStamp(LocalDateTime.now())
                        .build());
    }
}
