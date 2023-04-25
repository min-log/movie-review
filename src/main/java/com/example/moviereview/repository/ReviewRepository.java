package com.example.moviereview.repository;

import com.example.moviereview.entity.Member;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    //특정 영화의 리뷰
        // @EntityGraph : 조회시 Member객체 함께 로딩할 수 있도록 설정
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);


    //회원번호로 리뷰 제거
    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(@Param("member") Member member);

}
