package com.doosan.heritage.service;

import com.doosan.heritage.dto.guestbook.GuestbookDto;
import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.model.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService service;

    @Test
    public void _테스트() throws Exception{

        GuestbookDto dto = GuestbookDto.builder()
                .title("title..." + 111)
                .content("content..." + 111)
                .writer("user111")
                .build();


        //given
        service.register(dto);

        //when


        //then
    }

    @Test
    public void _테스트02() throws Exception{
        //given

        PageRequestDto pageRequestDto = PageRequestDto.builder().page(1).size(10).build();

        PageResultDto<Guestbook, GuestbookDto> list = service.getList(pageRequestDto);

        for (GuestbookDto guestbookDto : list.getDtoList()) {
            System.out.println("guestbookDto = " + guestbookDto);
        }


        System.out.println("list.isPrev() = " + list.isPrev());
        System.out.println("list.isNext() = " + list.isNext());
//        System.out.println("list.getTotalPage() = " + list.getTotalPage());


        list.getPageList().forEach(i -> System.out.println("i = " + i));


//
//        System.out.println("--------------------------------------------");

//        List<Integer> pageList = list.getPageList();
//
//        for (Integer integer : pageList) {
//            System.out.println("integer = " + integer);
//        }



        //when


        //then
    }

    @Test
    public void 검색조건_테스트() throws Exception{
        //given
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        //when
        PageResultDto<Guestbook, GuestbookDto> resultDto = service.getList(pageRequestDto);

        //boolean 타입이니 is로 선언가능
        System.out.println("resultDto.isPrev() = " + resultDto.isPrev());
        System.out.println("resultDto.isNext() = " + resultDto.isNext());
        System.out.println("resultDto.getTotalPage() = " + resultDto.getTotalPage());

        for (GuestbookDto guestbookDto : resultDto.getDtoList()) {
            System.out.println("guestbookDto = " + guestbookDto);
        }

        //then
    }



}