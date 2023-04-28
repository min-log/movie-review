package com.example.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Data
@AllArgsConstructor
@Builder
public class PageResultDTO<DTO,EN> {
    // 페이지에 전달할 값

    //현제 페이지 번호
    private int page;
    private int size;

    //dto 리스트
    private List<DTO> dtoList;
    //총페이지 번호
    private int totalPage;
    //시작페이지 번호,끝페이지 번호
    private int start;
    private int end;

    //이전 다음 버튼
    private boolean prev;
    private boolean next;

    //페이지 번호 목록
    private List<Integer> pageList;


    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());

    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/9.0))*9;
        start = tempEnd - 8;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }

}
