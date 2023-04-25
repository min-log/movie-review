package com.example.moviereview.repository;

import com.example.moviereview.entity.Member;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("저장 - 테스트 데이터")
    public void resister(){
        IntStream.rangeClosed(1,200).forEach(i->{
            //영화 번호 1~100
            Long mno = (long)(Math.random()*100) + 1;
            //리뷰어 번호
            Long mid = ((long)(Math.random()*100) + 1);
            Member member = Member.builder().mid(mid).build();
            Review review = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("영화 감상평..." + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    //
    @Test
    @DisplayName("특정 영화 리뷰 리스트 상세")
    public void testReviewList(){
        Movie movie = Movie.builder().mno(10L).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        result.forEach(i -> {
            System.out.println(i.getRno());
            System.out.println(i.getGrade());
            System.out.println(i.getText());
            System.out.println(i.getMember().getEmail());
            System.out.println("--------------------------");
        });

    }

    @Commit
    @Transactional
    @Test
    @DisplayName("회원 번호로 리뷰제거")
    public void testDeleteMember(){
        Long id = 3L;

        Member member = Member.builder()
                .mid(id).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(id);



    }

}