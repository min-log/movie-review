package com.example.moviereview.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    // 리뷰 고유번호
    private Long reviewnum;

    // 영화 고유번호
    private Long mno;

    // 고객 고유번호
    private Long mid;

    // 고객 닉네임
    private String nickname;

    private int grade;
    private String text;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
