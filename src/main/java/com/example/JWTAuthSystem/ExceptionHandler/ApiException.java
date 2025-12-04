package com.example.JWTAuthSystem.ExceptionHandler;


public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

}
