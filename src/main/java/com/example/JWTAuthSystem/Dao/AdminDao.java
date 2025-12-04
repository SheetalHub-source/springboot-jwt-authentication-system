package com.example.JWTAuthSystem.Dao;

import com.example.JWTAuthSystem.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
