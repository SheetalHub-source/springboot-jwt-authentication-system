package com.example.JWTAuthSystem.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {// Used to handle exceptions globally

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String,Object>> handleApiException(ApiException ex) {
        Map<String,Object> response = Map.of(
                "message", ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
