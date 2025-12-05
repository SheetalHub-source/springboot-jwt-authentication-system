package com.example.JWTAuthSystem.ServiceImpl;


import com.example.JWTAuthSystem.Dao.BookDao;
import com.example.JWTAuthSystem.Dao.UserDao;
import com.example.JWTAuthSystem.Dto.RequestDto.BookRequestDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.BookResponseDto;
import com.example.JWTAuthSystem.Dto.ResponseDto.ResponseModel;
import com.example.JWTAuthSystem.Model.Book;
import com.example.JWTAuthSystem.Model.User;
import com.example.JWTAuthSystem.Service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final UserDao userDao;

    public BookServiceImpl(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
    }

    public Boolean isApproveUser(UserDetails userDetails) {
        Optional<User> byEmail = userDao.findByEmail(userDetails.getUsername());

        if (!byEmail.isPresent()) {
            return false;
        }

        Boolean isActive = byEmail.get().getIsActive();
        return isActive != null ? isActive : false;
    }


    @Override
    public ResponseEntity<ResponseModel<List<BookResponseDto>>> getAllBooks(UserDetails userDetails) {
        if (!isApproveUser(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "User is not approved can't access this api", "Failed", HttpStatus.UNAUTHORIZED.value()));
        }
        List<Book> books = bookDao.findAll();

        List<BookResponseDto> list = books.stream()
                .map(book -> new BookResponseDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getPrice()
                ))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(list, "Books fetched Successfully", "Success", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ResponseModel<BookResponseDto>> getBookById(Long id, UserDetails userDetails) {
        if (!isApproveUser(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "User is not approved can't access this api", "Failed", HttpStatus.UNAUTHORIZED.value()));
        }
        Optional<Book> byId = bookDao.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel<>(null, "Book not present with this id", "Failed", HttpStatus.NOT_FOUND.value()));
        }
        Book book = byId.get();

        BookResponseDto bookResponseDto = new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice()
        );
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(bookResponseDto, "Book Successfully fetched", "Success", HttpStatus.OK.value()));
    }


    @Override
    public ResponseEntity<ResponseModel<BookResponseDto>> createBook(BookRequestDto bookRequestDto, UserDetails userDetails) {
        try {
            if (!isApproveUser(userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "User is not approved can't access this api", "Failed", HttpStatus.UNAUTHORIZED.value()));
            }

            Book book = new Book();
            book.setTitle(bookRequestDto.getTitle());
            book.setAuthor(bookRequestDto.getAuthor());
            book.setIsbn(bookRequestDto.getIsbn());
            book.setPrice(bookRequestDto.getPrice());
            book.setUserMail(userDetails.getUsername());

            Book savedBook = bookDao.save(book);

            BookResponseDto bookResponseDto = new BookResponseDto(
                    savedBook.getId(),
                    savedBook.getTitle(),
                    savedBook.getAuthor(),
                    savedBook.getIsbn(),
                    savedBook.getPrice()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel<>(
                            bookResponseDto,
                            "Book created Successfully",
                            "Success",
                            HttpStatus.CREATED.value()
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(null, e.getMessage(), "Failed", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Override
    public ResponseEntity<ResponseModel<BookResponseDto>> updateBook(Long id, BookRequestDto requestDto, UserDetails userDetails) {
        if (!isApproveUser(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "User is not approved can't access this api", "Failed", HttpStatus.UNAUTHORIZED.value()));
        }
        Optional<Book> byId = bookDao.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel<>(null, "No book present with the given id", "Failed", HttpStatus.NOT_FOUND.value()));
        }
        Book book = byId.get();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setPrice(requestDto.getPrice());

        Book updatedBook = bookDao.save(book);

        BookResponseDto bookResponseDto = new BookResponseDto(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getIsbn(),
                updatedBook.getPrice()
        );
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(bookResponseDto, "Book Updated successfully", "Success", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ResponseModel<String>> deleteBook(Long id, UserDetails userDetails) {
        if (!isApproveUser(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseModel<>(null, "User is not approved can't access this api", "Failed", HttpStatus.UNAUTHORIZED.value()));
        }
        Optional<Book> byId = bookDao.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel<>(null, "No book present with the given id", "Failed", HttpStatus.NOT_FOUND.value()));
        }
        Book book = byId.get();
        bookDao.delete(book);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>("Deleted", "Book Deleted successfully", "Success", HttpStatus.OK.value()));
    }

}
