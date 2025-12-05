package com.example.JWTAuthSystem.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
}
