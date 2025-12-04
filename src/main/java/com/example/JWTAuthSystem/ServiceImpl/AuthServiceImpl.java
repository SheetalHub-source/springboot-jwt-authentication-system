package com.example.JWTAuthSystem.ServiceImpl;

import com.example.JWTAuthSystem.Dto.RequestDto.LoginRequest;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.ExceptionHandler.WrongEmailException;
import com.example.JWTAuthSystem.Jwt.JwtUtil;
import com.example.JWTAuthSystem.Model.Admin;
import com.example.JWTAuthSystem.Model.User;
import com.example.JWTAuthSystem.Service.AdminService;
import com.example.JWTAuthSystem.Service.AuthService;
import com.example.JWTAuthSystem.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserService userService, AdminService adminService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.adminService = adminService;
    }

    public ResponseEntity<ResponseModel<?>> login(LoginRequest request) {
        if (!isValid(request.getEmail())) {
            throw new WrongEmailException("Email format is not valid, Please enter the correct email with correct email format.");
        }
        //Check Admin
        Optional<Admin> adminOpt = adminService.findByEmail(request.getEmail());
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "Admin password is wrong", "Failed", HttpStatus.UNAUTHORIZED.value()));
            }
            String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole(), null);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(token, "Admin Login Successfully", "Success", HttpStatus.OK.value()));

        }

        //  Check User
        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "User password is wrong", "Failed", HttpStatus.UNAUTHORIZED.value()));
            }
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getIsActive());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(token, "Admin Login Successfully", "Success", HttpStatus.OK.value()));

        }

        //  Not Found
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "User or Admin does not exist with the given credentials", "Success", HttpStatus.UNAUTHORIZED.value()));
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();
    }

}
