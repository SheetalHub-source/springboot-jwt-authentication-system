package com.example.JWTAuthSystem.Configuration;

import com.example.JWTAuthSystem.Dao.AdminDao;
import com.example.JWTAuthSystem.Dao.UserDao;
import com.example.JWTAuthSystem.Model.Admin;
import com.example.JWTAuthSystem.Model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
    private final UserDao userDao;
    private final AdminDao adminDao;
    public CustomeUserDetailsService(UserDao userDao, AdminDao adminDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> admin = adminDao.findByEmail(email);
        if (admin.isPresent()) {
            Admin a = admin.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(a.getEmail())
                    .password(a.getPassword())
                    .roles("ADMIN")   // adds ROLE_ADMIN internally
                    .build();
        }


        Optional<User> user = userDao.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(u.getEmail())
                    .password(u.getPassword())
                    .roles("USER")   // adds ROLE_USER internally
                    .build();
        }

        throw new UsernameNotFoundException("USER NOT FOUND");

    }
}
