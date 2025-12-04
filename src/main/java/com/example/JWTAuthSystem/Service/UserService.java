package com.example.JWTAuthSystem.Service;

import com.example.JWTAuthSystem.Dto.RequestDto.UserRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Dto.ResponseDto.UserResponseDto;
import com.example.JWTAuthSystem.Model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseEntity<ResponseModel<UserResponseDto>> signUp(UserRequestDto userRequestDto);

    Optional<User> findByEmail(String email);

    Optional<User> save(User user);

    ResponseEntity<ResponseModel<List<UserResponseDto>>> getAllUsers();

    ResponseEntity<ResponseModel<UserResponseDto>> getUserByEmail(String email);
}
