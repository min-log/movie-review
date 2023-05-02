package com.example.moviereview.controller;

import com.example.moviereview.Service.ReviewService;
import com.example.moviereview.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        log.info("review list-----------------");
        log.info("mno : {}", mno);
        List<ReviewDTO> reviewList = reviewService.getReviewList(mno);

        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> saveReview(@RequestBody ReviewDTO reviewDTO){
        log.info("review add-----------------");
        log.info("reviewDTO : {} ",reviewDTO);
        Long result = reviewService.registerReview(reviewDTO);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @PostMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(
            @PathVariable("reviewnum") Long reviewnum,
            @RequestBody ReviewDTO reviewDTO){
        log.info("review modify -----------------");
        log.info("reviewnum : {} ",reviewnum);
        log.info("reviewDTO : {} ",reviewDTO);

        reviewService.modifyReview(reviewDTO);

        return new ResponseEntity<>(reviewnum,HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> deleteReview(@PathVariable("reviewnum") Long reviewnum){
        log.info("review delete -----------------");
        log.info("reviewnum : {} ",reviewnum);
        reviewService.removeReview(reviewnum);

        return new ResponseEntity<>(reviewnum,HttpStatus.OK);
    }


}
