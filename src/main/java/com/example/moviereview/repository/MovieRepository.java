package com.example.moviereview.repository;

import com.example.moviereview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    //페이지 리스트 - 리뷰 개수, 평점
    @Query(value = "select m , mi , avg(coalesce(r.grade,0)), count(distinct r) " +
            " from Movie m "
            + " left outer join MovieImage mi " +
            " on mi.movie = m "
            + " left outer join Review r " +
            " on r.movie = m " +
            " group by m , mi ",
             nativeQuery = false )
    Page<Object[]> getListPage(Pageable pageable);


    // 상세페이지 - 특정 영화 조회,이미지, 리뷰 갯수, 조회수
    @Query("select m, mi ,avg(coalesce(r.grade,0)),  count(r)" +
            " from Movie m " +
            " left outer join MovieImage mi on mi.movie = m " +
            " left outer join Review  r on r.movie = m "+
            " where m.mno = :mno " +
            " group by mi")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);

}
