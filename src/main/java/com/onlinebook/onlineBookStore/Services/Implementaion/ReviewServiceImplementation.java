package com.onlinebook.onlineBookStore.Services.Implementaion;

import com.onlinebook.onlineBookStore.DTO.ReviewDTO;
import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.Entity.Review;
import com.onlinebook.onlineBookStore.Entity.UserInfo;
import com.onlinebook.onlineBookStore.ExceptionHandeling.CustomExceptionHandel;
import com.onlinebook.onlineBookStore.Mapper.ReviewMapper;
import com.onlinebook.onlineBookStore.Repository.BookRepository;
import com.onlinebook.onlineBookStore.Repository.ReviewRepository;
import com.onlinebook.onlineBookStore.Repository.UserInfoRepository;
import com.onlinebook.onlineBookStore.Services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImplementation implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserInfoRepository userInfoRepository;
    private final BookServiceImplementation bookServiceImplementation;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO){
        Book book = bookRepository.findById(reviewDTO.getBookId())
                .orElseThrow(()-> new CustomExceptionHandel(
                        "Book Not Found with Id : " +reviewDTO.getBookId(), HttpStatus.NOT_FOUND.value()));
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        UserInfo userInfo = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new CustomExceptionHandel("User Not Found",HttpStatus.NOT_FOUND.value()));

        Review review = ReviewMapper.toEntity(reviewDTO,book, userInfo);

        Review saveReview = reviewRepository.save(review);
        return ReviewMapper.toDto(saveReview);

    }

    @Override
    public ReviewDTO getReviewById(int id){
        Review review = reviewRepository.findById(id)
                .orElseThrow(()->new CustomExceptionHandel("Review id not found with id : " + id, HttpStatus.NOT_FOUND.value()));

        return ReviewMapper.toDto(review);
    }

    @Override
    public List<ReviewDTO> getReviewByBookId(int bookId){
        if(!bookRepository.existsById(bookId)){
            throw new CustomExceptionHandel("Book not found with id: "+bookId, HttpStatus.NOT_FOUND.value());
        }
        List<ReviewDTO> reviews = reviewRepository.findAllByBookId(bookId)
                .stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());

        return reviews;
    }
    @Override
    public ReviewDTO updateReview(int id, ReviewDTO reviewDTO){
        Review review = reviewRepository.findById(id)
                .orElseThrow(()->new CustomExceptionHandel("Review not found with ID : " +id, HttpStatus.NOT_FOUND.value()));

        Book book = bookRepository.findById(reviewDTO.getBookId())
                .orElseThrow(()->new CustomExceptionHandel("Book not found with id : " + reviewDTO.getBookId(), HttpStatus.NOT_FOUND.value()));

        UserInfo userInfo = new UserInfo();
        Review updatedReview = ReviewMapper.toEntity(reviewDTO,book,userInfo);
        updatedReview.setId(id);

        Review savedReview = reviewRepository.save(updatedReview);
        return ReviewMapper.toDto(savedReview);

    }
    @Override
    public void deleteReview(int id){
        if(!reviewRepository.existsById(id)){
            throw  new CustomExceptionHandel("Review not found with id: " +id, HttpStatus.NO_CONTENT.value());
        }
        reviewRepository.deleteById(id);
    }
}
