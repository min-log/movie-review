package com.example.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@Builder
public class PageRequestDTO {
    //전달 받는 페이지 값
    private int page;
    private int size;
    // 검색 조건
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 9;
    }
    
    //pageable 구해주는 메서드
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page - 1 ,size,sort);
    }
}
