package com.example.JWTAuthSystem.Service;

import com.example.JWTAuthSystem.Dto.RequestDto.BookRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.BookResponseDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface BookService {
    ResponseEntity<ResponseModel<List<BookResponseDto>>> getAllBooks(UserDetails userDetails);

    ResponseEntity<ResponseModel<BookResponseDto>> getBookById(Long id, UserDetails userDetails);

    ResponseEntity<ResponseModel<BookResponseDto>> createBook(BookRequestDto requestDto, UserDetails userDetails);

    ResponseEntity<ResponseModel<BookResponseDto>> updateBook(Long id, BookRequestDto requestDto, UserDetails userDetails);

    ResponseEntity<ResponseModel<String>> deleteBook(Long id, UserDetails userDetails);

}
