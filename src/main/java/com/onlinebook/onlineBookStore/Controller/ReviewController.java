package com.onlinebook.onlineBookStore.Controller;

import com.onlinebook.onlineBookStore.DTO.ApiResponse;
import com.onlinebook.onlineBookStore.DTO.ReviewDTO;
import com.onlinebook.onlineBookStore.Services.Implementaion.ReviewServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController extends ResponseUtils {
    private final ReviewServiceImplementation reviewServiceImplementation;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDTO>> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createReviews = reviewServiceImplementation.createReview(reviewDTO);
        return created(createReviews);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewDTO>> getReviewById(@PathVariable int id){
        ReviewDTO reviewDTO = reviewServiceImplementation.getReviewById(id);
        return  ok(reviewDTO);
    }
    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getReviewByBookId(@PathVariable int bookId){
        List<ReviewDTO>reviewDTOS  = reviewServiceImplementation.getReviewByBookId(bookId);
        return ok("Found",reviewDTOS);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewDTO>> updateReview(@Valid
                        @PathVariable int id,
                        @RequestBody ReviewDTO reviewDTO){
        ReviewDTO updateReview = reviewServiceImplementation.updateReview(id,reviewDTO);
        return ok("Review Update Sucessfully", updateReview);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable int id){
        reviewServiceImplementation.deleteReview(id);
        return noContent();
    }
}
