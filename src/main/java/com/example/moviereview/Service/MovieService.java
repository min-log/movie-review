package com.example.moviereview.Service;

import com.example.moviereview.dto.MovieDTO;
import com.example.moviereview.dto.MovieImageDTO;
import com.example.moviereview.dto.PageRequestDTO;
import com.example.moviereview.dto.PageResultDTO;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    // 저장
    Long register(MovieDTO movieDTO);

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO){
        Map<String,Object> entityMap = new HashMap<>();
        //movie 객체
        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();
        entityMap.put("movie",movie);


        //movieImage 객체가 있다면 movieImage 객체로 반환
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        if (imageDTOList != null && imageDTOList.size() > 0){
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie).build();
                return movieImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList",movieImageList);
        }
        // movieImage가 없다면 movie 로 반환
        return entityMap;

    }

    //start 리스트 화면
    PageResultDTO<MovieDTO,Object[]> getList(PageRequestDTO pageRequestDTO);

    default MovieDTO entitiesToDTO(Movie movie,List<MovieImage> movieImageList, Double avg, Long reviewCnt){
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate()).build(); // [0] - movie

        List<MovieImageDTO> movieImageDTOList = movieImageList.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);// [1] - movieImage
        movieDTO.setAvg(avg);// [2] - avg
        movieDTO.setReviewCnt(reviewCnt.intValue());// [3] - reviewCnt

        return movieDTO;

    }
    //end 리스트 화면

    //start 상세
    MovieDTO getMovie(Long mno);
}
