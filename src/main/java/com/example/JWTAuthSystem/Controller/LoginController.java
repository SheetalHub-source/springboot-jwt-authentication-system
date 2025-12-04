package com.example.JWTAuthSystem.Controller;

import com.example.JWTAuthSystem.Dto.RequestDto.LoginRequest;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<?>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
