package com.example.JWTAuthSystem.Service;

import com.example.JWTAuthSystem.Dto.RequestDto.LoginRequest;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<ResponseModel<?>> login(LoginRequest request);
}
