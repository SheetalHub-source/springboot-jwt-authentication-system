package com.example.JWTAuthSystem.ServiceImpl;

import com.example.JWTAuthSystem.Dao.AdminDao;
import com.example.JWTAuthSystem.Dto.RequestDto.AdminRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.AdminResponseDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Dto.ResponseDto.UserResponseDto;
import com.example.JWTAuthSystem.ExceptionHandler.WrongEmailException;
import com.example.JWTAuthSystem.Model.Admin;
import com.example.JWTAuthSystem.Model.User;
import com.example.JWTAuthSystem.Service.AdminService;
import com.example.JWTAuthSystem.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminDao adminDao;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AdminServiceImpl(AdminDao adminDao, PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.adminDao = adminDao;
        this.userService = userService;
    }


    @Override
    public ResponseEntity<ResponseModel<AdminResponseDto>> signUp(AdminRequestDto adminRequestDto) {

        if (!isValid(adminRequestDto.getEmail())) {
            throw new WrongEmailException("Email format is not valid, Please enter the correct email with correct email format.");
        }

        if (adminDao.findByEmail(adminRequestDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseModel<>("Email already in use", "error", HttpStatus.CONFLICT.value()));
        }

        Admin savedAdmin = adminDao.save(
                Admin.builder()
                        .username(adminRequestDto.getUsername())
                        .email(adminRequestDto.getEmail())
                        .password(passwordEncoder.encode(adminRequestDto.getPassword()))
                        .build()
        );
        AdminResponseDto data = AdminResponseDto.builder()
                .username(savedAdmin.getUsername())
                .email(savedAdmin.getEmail())
                .role(savedAdmin.getEmail())
                .id(savedAdmin.getId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseModel<>(data, "Admin created successfully", "success", HttpStatus.CREATED.value()));
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return adminDao.findByEmail(email);
    }

    @Override
    public ResponseEntity<ResponseModel<UserResponseDto>> approveUserActiveStatus(String userEmail, Boolean isApprove) {
        if (!isValid(userEmail)) {
            throw new WrongEmailException("Email format is not valid, Please enter the correct email with correct email format.");
        }
        Optional<User> byEmail = userService.findByEmail(userEmail);
        if (!byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel<>(null, "User with this email is not present", "Failed", HttpStatus.NOT_FOUND.value()));
        }
        User user = byEmail.get();
        user.setIsActive(isApprove);
        User savedUser = userService.save(user).get();
        UserResponseDto data = UserResponseDto.builder()
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .isActive(savedUser.getIsActive())
                .email(savedUser.getEmail())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseModel<>(data, "User's active status update Successfully", "Success", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ResponseModel<List<AdminResponseDto>>> getAllAdmin() {
        List<Admin> all = adminDao.findAll();
        List<AdminResponseDto> responseDtos = new ArrayList<>();
        for (Admin admin : all) {
            AdminResponseDto adminResponseDto = new AdminResponseDto(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getRole());
            responseDtos.add(adminResponseDto);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseModel<>(responseDtos, "All Admin fetched Successfully", "Success", HttpStatus.FOUND.value()));
    }


    @Override
    public ResponseEntity<ResponseModel<AdminResponseDto>> getAdminByEmail(String email) {
        if (!isValid(email)) {
            throw new WrongEmailException("Email format is not valid, Please enter the correct email with correct email format.");
        }
        Optional<Admin> byEmail = adminDao.findByEmail(email);
        if (!byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel<>(null, "Admin with this email is not present", "Failed", HttpStatus.NOT_FOUND.value()));
        }
        Admin admin = byEmail.get();
        AdminResponseDto data = AdminResponseDto.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole())
                .build();
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ResponseModel<>(data, "Admin fetched Successfully", "Success", HttpStatus.FOUND.value()));
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();
    }
}
