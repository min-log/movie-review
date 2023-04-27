package com.example.moviereview.Service;

import com.example.moviereview.dto.MovieDTO;
import com.example.moviereview.dto.PageRequestDTO;
import com.example.moviereview.dto.PageResultDTO;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.MovieImage;
import com.example.moviereview.repository.MovieImageRepository;
import com.example.moviereview.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    // 저장
    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {
        //Map변환
        Map<String, Object> entityMap = dtoToEntity(movieDTO);

        // entity 변환
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
        
        // 데이터 저장
        movieRepository.save(movie);
        movieImageList.forEach(i->{
            movieImageRepository.save(i);  
        });
        
        return movie.getMno();
    }

    //목록 처리
    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);
        Function<Object[],MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie) arr[0],
                (List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
                (Double)arr[2],
                (Long)arr[3])
        );
        return new PageResultDTO<>(result,fn);
    }
}
