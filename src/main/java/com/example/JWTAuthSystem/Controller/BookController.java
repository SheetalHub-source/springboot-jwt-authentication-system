package com.example.JWTAuthSystem.Controller;

import com.example.JWTAuthSystem.Dto.RequestDto.BookRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.BookResponseDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<BookResponseDto>>> getAllBooks(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} requested all books", userDetails.getUsername());
        return bookService.getAllBooks(userDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<BookResponseDto>> getBookById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} requested book with ID {}", userDetails.getUsername(), id);
        return bookService.getBookById(id, userDetails);
    }

    @PostMapping
    public ResponseEntity<ResponseModel<BookResponseDto>> createBook(
           @RequestBody BookRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} is creating a book with title '{}'", userDetails.getUsername(), requestDto.getTitle());
        return bookService.createBook(requestDto, userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<BookResponseDto>> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} is updating book with ID {}", userDetails.getUsername(), id);
        return bookService.updateBook(id, requestDto, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<String>> deleteBook(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} is deleting book with ID {}", userDetails.getUsername(), id);
        return bookService.deleteBook(id, userDetails);
    }
}
