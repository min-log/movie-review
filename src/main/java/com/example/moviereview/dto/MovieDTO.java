package com.example.moviereview.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private Long mno;
    private String title;


    // 리스트에서 필요한 내용
    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();
    //영화 평균 평점
    private double avg;
    //리뷰수
    private int reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    
}
