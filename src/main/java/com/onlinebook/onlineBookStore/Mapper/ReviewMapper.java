package com.onlinebook.onlineBookStore.Mapper;

import com.onlinebook.onlineBookStore.DTO.BookDTO;
import com.onlinebook.onlineBookStore.DTO.ReviewDTO;
import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.Entity.Review;

import java.util.List;

public class ReviewMapper {

    public static ReviewDTO toDto(Review review){

        return new ReviewDTO(

                review.getId(),
                review.getBook().getId(),
                review.getReviewerName(),
                review.getRating(),
                review.getComment(),
                null
        );

    }

    public static Review toEntity(ReviewDTO reviewDTO, Book book){

        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setBook(book);
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        return review;

    }
}
