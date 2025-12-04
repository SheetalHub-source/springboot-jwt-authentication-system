package com.example.JWTAuthSystem.Controller;

import com.example.JWTAuthSystem.Dto.RequestDto.UserRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Dto.ResponseDto.UserResponseDto;
import com.example.JWTAuthSystem.ExceptionHandler.ApiException;
import com.example.JWTAuthSystem.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseModel<UserResponseDto>> signUp(@RequestBody UserRequestDto userRequestDto) {
       return userService.signUp(userRequestDto);
    }
    @GetMapping
    public ResponseEntity<ResponseModel<List<UserResponseDto>>> getAllUser(){
        return userService.getAllUsers();
    }
    @GetMapping("/{email}")
    public ResponseEntity<ResponseModel<UserResponseDto>> getUserByEmail(@PathVariable String email){
       return userService.getUserByEmail(email);
    }
}
