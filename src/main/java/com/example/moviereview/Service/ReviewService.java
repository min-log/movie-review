package com.example.moviereview.Service;

import com.example.moviereview.dto.ReviewDTO;
import com.example.moviereview.entity.Member;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.Review;

import java.util.List;

public interface ReviewService {

    // 한 영화의 모든 리뷰 조회
    List<ReviewDTO> getReviewList(Long mno);

    // 한 영화 리뷰 추가
    Long registerReview(ReviewDTO reviewDTO);

    // 한 영화 특정 리뷰 수정
    void modifyReview(ReviewDTO reviewDTO);

    // 한 영화 특정 리뷰 삭제
    void removeReview(Long revieNum);


    // dto -> entity
    default Review dtoToEntity(ReviewDTO reviewDTO){
        // 고객
        Member member = Member.builder()
                .mid(reviewDTO.getMid()).build();
        // 영화
        Movie movie = Movie.builder()
                .mno(reviewDTO.getMno()).build();

        Review review = Review.builder()
                .rno(reviewDTO.getReviewnum())
                .text(reviewDTO.getText())
                .grade(reviewDTO.getGrade())
                .member(member)
                .movie(movie)
                .build();

        return review;
    }

    //entity -> dto
    default ReviewDTO entityToDto(Review review){
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .mno(review.getMovie().getMno()) // 영화 
                .mid(review.getMember().getMid()) // 고객
                .nickname(review.getMember().getNickname()) // 고객
                .reviewnum(review.getRno()) //리뷰
                .text(review.getText())
                .grade(review.getGrade())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();

        return reviewDTO;
    }




}
