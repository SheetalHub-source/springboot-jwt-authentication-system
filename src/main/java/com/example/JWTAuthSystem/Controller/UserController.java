package com.example.JWTAuthSystem.Controller;

import com.example.JWTAuthSystem.Dto.RequestDto.UserRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Dto.ResponseDto.UserResponseDto;
import com.example.JWTAuthSystem.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "UserApis")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseModel<UserResponseDto>> signUp(@RequestBody UserRequestDto userRequestDto) {
        return userService.signUp(userRequestDto);
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<UserResponseDto>>> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseModel<UserResponseDto>> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
