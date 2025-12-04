package com.example.JWTAuthSystem.Controller;

import com.example.JWTAuthSystem.Dto.RequestDto.AdminRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.AdminResponseDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Dto.ResponseDto.UserResponseDto;
import com.example.JWTAuthSystem.ExceptionHandler.ApiException;
import com.example.JWTAuthSystem.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseModel<AdminResponseDto>> signUp(@RequestBody AdminRequestDto adminRequestDto) {
            return adminService.signUp(adminRequestDto);
    }

    @PatchMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseModel<UserResponseDto>> approveUser(@RequestParam String userEmail, @RequestParam Boolean isApprove) {
          return adminService.approveUserActiveStatus(userEmail,isApprove);
        }
        @GetMapping
        public ResponseEntity<ResponseModel<List<AdminResponseDto>>> getAllAdmin(){
            return adminService.getAllAdmin();
        }
        @GetMapping("/{email}")
        public ResponseEntity<ResponseModel<AdminResponseDto>> getAdminByEmail(@PathVariable String email){
            return adminService.getAdminByEmail(email);
        }

    }

