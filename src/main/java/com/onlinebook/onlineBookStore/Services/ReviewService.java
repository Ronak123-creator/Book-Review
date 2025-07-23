package com.onlinebook.onlineBookStore.Services;

import com.onlinebook.onlineBookStore.DTO.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview (ReviewDTO reviewDTO);
    ReviewDTO getReviewById(int id);
    List<ReviewDTO> getReviewByBookId(int bookId);
    ReviewDTO updateReview(int id, ReviewDTO reviewDTO);
    void deleteReview(int id);
}
