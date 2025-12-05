package com.example.JWTAuthSystem.Service;

import com.example.JWTAuthSystem.Dto.RequestDto.AdminRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.AdminResponseDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Dto.ResponseDto.UserResponseDto;
import com.example.JWTAuthSystem.Model.Admin;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    ResponseEntity<ResponseModel<AdminResponseDto>> signUp(AdminRequestDto adminRequestDto);

    Optional<Admin> findByEmail(String email);

    ResponseEntity<ResponseModel<UserResponseDto>> approveUserActiveStatus(String userEmail);

    ResponseEntity<ResponseModel<List<AdminResponseDto>>> getAllAdmin();

    ResponseEntity<ResponseModel<AdminResponseDto>> getAdminByEmail(String email);
}
