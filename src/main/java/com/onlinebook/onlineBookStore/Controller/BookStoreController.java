package com.onlinebook.onlineBookStore.Controller;

import com.onlinebook.onlineBookStore.DTO.ApiResponse;
import com.onlinebook.onlineBookStore.DTO.BookDTO;
import com.onlinebook.onlineBookStore.DTO.InventoryUpdateDto;
import com.onlinebook.onlineBookStore.Services.Implementaion.BookServiceImplementation;
import com.onlinebook.onlineBookStore.Utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookStoreController {
    private final BookServiceImplementation bookServiceImplementation;
    private final ResponseUtils responseUtils;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks() {
        List<BookDTO> books = bookServiceImplementation.getAllBooks();
        return responseUtils.ok("All books retrieved", books);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook (@Valid @RequestBody BookDTO bookDTO){
        BookDTO createdBook = bookServiceImplementation.createBook(bookDTO);
        return responseUtils.created(createdBook);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable int id){
        BookDTO bookDTO = bookServiceImplementation.getBookById(id);
        return responseUtils.ok(bookDTO);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBookByCategory(
            @PathVariable String category
    ){
        List<BookDTO> books = bookServiceImplementation.getBookByCategory(category);
        return responseUtils.ok("All books retrieved", books);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/stock")
    public ResponseEntity<ApiResponse<BookDTO>> updateStock(@RequestBody InventoryUpdateDto dto){
        BookDTO updateStock = bookServiceImplementation.updateQuantity(dto);
        return responseUtils.ok("Stock Update Successfully", updateStock);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@Valid @PathVariable int id,
                                                           @RequestBody BookDTO bookDTO){
        BookDTO updateBook = bookServiceImplementation.updateBook(id, bookDTO);
        return responseUtils.ok("Book update successfully",updateBook);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable int id){
        bookServiceImplementation.deleteBook(id);
        return responseUtils.noContent();
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDTO>>> searchBook(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {
        List<BookDTO> bookList = bookServiceImplementation.searchBook(category, title, author);
        return responseUtils.ok("Search successful", bookList);
    }

}
