package com.onlinebook.onlineBookStore.Repository;

import com.onlinebook.onlineBookStore.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByBookId(int bookId);
}
