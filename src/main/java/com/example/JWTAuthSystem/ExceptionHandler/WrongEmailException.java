package com.example.JWTAuthSystem.ExceptionHandler;

public class WrongEmailException extends RuntimeException{
    public WrongEmailException(String message){
        super(message);
    }
}
