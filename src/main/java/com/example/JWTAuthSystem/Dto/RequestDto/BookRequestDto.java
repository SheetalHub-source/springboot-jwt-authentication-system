package com.example.JWTAuthSystem.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class BookRequestDto {
    private String title;
    private String author;
    private String isbn;
    private Double price;
}
