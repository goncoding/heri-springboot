package com.doosan.heritage.dto.page;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
public class PageResultDto<EN,DTO> {

    //dto 리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;
    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;
    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;
    //이전, 다음
    private boolean prev, next;
    //화면에 출력될 페이지 번호
    private List<Integer> pageList;



    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        //findAll로 엔티티 리스트를 가져와서 -> 엔티티를 dto stream 타입으로 반환 할 것을 지정
        //fn은 serviceImpl 단에서 entitytoDto 를 실행 후 담기게 된다.
        Stream<DTO> dtoStream = result.stream().map(fn);
        dtoList = dtoStream.collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());

    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; //0부터 시작 , 1 추가
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        //boxed를 해야 제너릭에 해당 Integer 타입이 담기고 collector 타입을 불러올 수 있다.
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }


}
