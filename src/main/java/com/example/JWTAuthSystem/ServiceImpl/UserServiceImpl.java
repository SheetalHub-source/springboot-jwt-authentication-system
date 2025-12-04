package com.example.JWTAuthSystem.ServiceImpl;

import com.example.JWTAuthSystem.Dao.UserDao;
import com.example.JWTAuthSystem.Dto.RequestDto.UserRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Dto.ResponseDto.UserResponseDto;
import com.example.JWTAuthSystem.ExceptionHandler.WrongEmailException;
import com.example.JWTAuthSystem.Model.User;
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
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<ResponseModel<UserResponseDto>> signUp(UserRequestDto userRequestDto) {
        if (!isValid(userRequestDto.getEmail())) {
            throw new WrongEmailException("Email format is not valid, Please enter the correct email with correct email format.");
        }
        if (userDao.findByEmail(userRequestDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseModel<>("Email already in use", "error", HttpStatus.CONFLICT.value()));
        }

        User savedUser = userDao.save(
                User.builder()
                        .username(userRequestDto.getUsername())
                        .email(userRequestDto.getEmail())
                        .password(passwordEncoder.encode(userRequestDto.getPassword()))
                        .isActive(false)
                        .build()
        );
        UserResponseDto userResponseDto = new UserResponseDto(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getIsActive()
        );


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseModel<>(userResponseDto, "User created successfully", "success", HttpStatus.CREATED.value()));

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userDao.save(user));
    }

    @Override
    public ResponseEntity<ResponseModel<List<UserResponseDto>>> getAllUsers() {
        List<User> all = userDao.findAll();
        List<UserResponseDto> responseDtos = new ArrayList<>();
        for (User user : all) {
            UserResponseDto userResponseDto = new UserResponseDto(user.getUsername(), user.getEmail(), user.getRole(), user.getIsActive());
            responseDtos.add(userResponseDto);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseModel<>(responseDtos, "All Users fetched Successfully", "Success", HttpStatus.FOUND.value()));
    }

    @Override
    public ResponseEntity<ResponseModel<UserResponseDto>> getUserByEmail(String email) {
        if (!isValid(email)) {
            throw new WrongEmailException("Email format is not valid, Please enter the correct email with correct email format.");
        }
        Optional<User> byEmail = userDao.findByEmail(email);
        if (!byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel<>(null, "Admin with this email is not present", "Failed", HttpStatus.NOT_FOUND.value()));
        }
        User user = byEmail.get();
        UserResponseDto data = UserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .build();
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ResponseModel<>(data, "User fetched Successfully", "Success", HttpStatus.FOUND.value()));

    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();
    }
}

