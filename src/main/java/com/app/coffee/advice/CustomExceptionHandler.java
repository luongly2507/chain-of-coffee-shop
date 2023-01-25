package com.app.coffee.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.coffee.exception.BadRequestException;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.payload.response.ErrorMessageResponse;

@RestControllerAdvice
// Advice: Khi bắt đc exception thêm advice vào nó sẽ xử lý exception
public class CustomExceptionHandler {
        // Internal Server Error Handler
        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleException(Exception ex) {
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ErrorMessageResponse
                                                .builder()
                                                .message("Internal Server Error")
                                                .statusCode(500)
                                                .timeStamp(LocalDateTime.now())
                                                .build());
        }
        // Bad Request Exception Handler
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(ErrorMessageResponse
                                                .builder()
                                                .message(ex.getMessage())
                                                .statusCode(400)
                                                .timeStamp(LocalDateTime.now())
                                                .build());
        }
        // Conflict Exception Handler
        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<?> handleConflictException(ConflictException ex) {
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(ErrorMessageResponse
                                                .builder()
                                                .message(ex.getMessage())
                                                .statusCode(409)
                                                .timeStamp(LocalDateTime.now())
                                                .build());
        }
        // Resource Not Found Exception Handler
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(ErrorMessageResponse
                                                .builder()
                                                .message(ex.getMessage())
                                                .statusCode(404)
                                                .timeStamp(LocalDateTime.now())
                                                .build());
        }
        // Exception for Validation
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleConstraintViolationException(MethodArgumentNotValidException ex) {
                Map<String, String> errorMap = new HashMap<>();
                ex.getBindingResult().getFieldErrors().forEach(error -> {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                });
                return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }        
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<?>  handleDataIntegrityViolationException(DataIntegrityViolationException ex){
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(ErrorMessageResponse
                                                .builder()
                                                .message(ex.getMessage())
                                                .statusCode(400)
                                                .timeStamp(LocalDateTime.now())
                                                .build());
        }
}
