package com.onlinebook.onlineBookStore.Mapper;

import com.onlinebook.onlineBookStore.DTO.BookDTO;
import com.onlinebook.onlineBookStore.Entity.Book;

public class BookMapper {

    public static BookDTO toDto(Book book){
        return new BookDTO(
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getPublishedDate(),
                book.getCategory(),
                book.getCoverImage(),
                book.getCreatedDate(),
                book.getUpdatedDate()
        );
    }
    public static Book toEntity(BookDTO dto){
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setCategory(dto.getCategory());
        book.setPublishedDate(dto.getPublicationDate());
        book.setCoverImage(dto.getCoverImage());

        return book;
    }
}
