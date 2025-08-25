package com.onlinebook.onlineBookStore.Mapper;

import com.onlinebook.onlineBookStore.DTO.ReviewDTO;
import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.Entity.Review;
import com.onlinebook.onlineBookStore.Entity.UserInfo;

public class ReviewMapper {

    public static ReviewDTO toDto(Review review){

        return new ReviewDTO(

                review.getId(),
                review.getBook().getId(),
//                review.getReviewerName(),
                review.getUser().getName(),
                review.getRating(),
                review.getComment(),
                null
        );

    }

    public static Review toEntity(ReviewDTO reviewDTO, Book book, UserInfo user){

        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setBook(book);
        review.setUser(user);
//        review.setReviewerName(reviewDTO.getReviewerName());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        return review;

    }
}
