package com.example.moviereview.Service;

import com.example.moviereview.dto.MovieDTO;
import com.example.moviereview.dto.MovieImageDTO;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    // 저장
    Long register(MovieDTO movieDTO);
    //리스트 출력
    // List<MovieDTO> getMovieList();

    //movieDTO - > movie, movieImage 객채를 가지고 있다. Map으로 받아준다.
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

}
