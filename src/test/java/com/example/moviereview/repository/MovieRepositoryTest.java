package com.example.moviereview.repository;

import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.MovieImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository imageRepository;



    @Transactional
    @Commit
    @Test
    @DisplayName("저장 - 테스트 데이터")
    public void insertMovies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Movie movie = Movie.builder()
                    .title("title..."+i ).build();
            System.out.println("-----------------");
            movieRepository.save(movie);
            int count = (int)(Math.random() * 5) + 1;
            for (int j=0; j < count; j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg").build();
                imageRepository.save(movieImage);
            }
            System.out.println("-----------------");
        });
    }

    @Test
    @DisplayName("전체 리스트 출력")
    public void testListPage(){
        PageRequest pageRequest = PageRequest.of(
                0,10,
                Sort.by(Sort.Direction.DESC,"mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);
        for (Object[] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    @DisplayName("상세페이지")
    public void test2(){
        List<Object[]> result = movieRepository.getMovieWithAll(10L);
        System.out.println(result);
        System.out.println("------------------------");
        for (Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }
}