package com.example.moviereview.Service;

import com.example.moviereview.dto.ReviewDTO;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.Review;
import com.example.moviereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;


    // 전체 리뷰 리스트
    @Override
    public List<ReviewDTO> getReviewList(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        List<ReviewDTO> resultDto = result.stream().map(i -> entityToDto(i)).collect(Collectors.toList());
        return resultDto;
    }

    @Override
    public Long registerReview(ReviewDTO reviewDTO) {
        Review result = reviewRepository.save(dtoToEntity(reviewDTO));
        return result.getRno();
    }

    @Override
    public void modifyReview(ReviewDTO reviewDTO) {
        Long reviewnum = reviewDTO.getReviewnum();
        Optional<Review> list = reviewRepository.findById(reviewnum);
        // 일치하는 리뷰가 있을경우
        if (list.isPresent()){
            Review review = list.get();
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());
            reviewRepository.save(review);
        }

    }

    @Override
    public void removeReview(Long reviewNum) {
        reviewRepository.deleteById(reviewNum);
    }

}
