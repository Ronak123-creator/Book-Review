package com.onlinebook.onlineBookStore.Repository;

import com.onlinebook.onlineBookStore.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {

     boolean existsByTitleIgnoreCaseContaining(String title);
     List<Book> findByCategoryIgnoreCaseContaining(String category);
     List<Book> findByTitleIgnoreCaseContaining(String title);
     List<Book> findByAuthorIgnoreCaseContaining(String author);

     List<Book> findByCategoryIgnoreCaseContainingAndTitleIgnoreCaseContaining(String category, String Title);

     List<Book> findByTitleIgnoreCaseContainingAndAuthorIgnoreCaseContaining(String title, String author);
     List<Book> findByCategoryIgnoreCaseContainingAndAuthorIgnoreCaseContaining(String category, String author);
     List<Book> findByCategoryIgnoreCaseContainingAndTitleIgnoreCaseContainingAndAuthorIgnoreCaseContaining(
            String category, String title, String author);
}
