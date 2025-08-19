package com.onlinebook.onlineBookStore.Services;

import com.onlinebook.onlineBookStore.DTO.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks();
    BookDTO createBook(BookDTO dto);
    BookDTO getBookById(int id);
    BookDTO updateBook(int id, BookDTO dto);
    BookDTO deleteBook(int id);
    List<BookDTO> searchBook(String category, String title, String author);




}
