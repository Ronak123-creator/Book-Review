package com.onlinebook.onlineBookStore.Controller;

import com.onlinebook.onlineBookStore.DTO.ApiResponse;
import com.onlinebook.onlineBookStore.DTO.BookDTO;
import com.onlinebook.onlineBookStore.Services.Implementaion.BookServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookStoreController {
    private final BookServiceImplementation bookServiceImplementation;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks() {
        List<BookDTO> books = bookServiceImplementation.getAllBooks();
        return new ResponseUtils().ok("All books retrieved", books);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook (@Valid @RequestBody BookDTO bookDTO){
        BookDTO createdBook = bookServiceImplementation.createBook(bookDTO);
        return new ResponseUtils().created(createdBook);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable int id){
        BookDTO bookDTO = bookServiceImplementation.getBookById(id);
        return new ResponseUtils().ok(bookDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@Valid @PathVariable int id,
                                                           @RequestBody BookDTO bookDTO){
        BookDTO updateBook = bookServiceImplementation.updateBook(id, bookDTO);
        return new ResponseUtils().ok("Book update successfully",updateBook);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable int id){
        bookServiceImplementation.deleteBook(id);
        return new ResponseUtils().noContent();
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDTO>>> searchBook(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {
        List<BookDTO> bookList = bookServiceImplementation.searchBook(category, title, author);
        return new ResponseUtils().ok("Search successful", bookList);
    }

}
