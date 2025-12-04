package com.example.JWTAuthSystem.Dao;

import com.example.JWTAuthSystem.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book, Long> {
}
