package com.doosan.heritage.service;

import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.dto.replyTest.BoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    BoardService service;

    @Test
    public void _테스트() throws Exception{
        //given
        BoardDto dto = BoardDto.builder()
                .title("test..")
                .content("test..")
                .writerEmail("user55@aaa.com")
                .build();

        Long register = service.register(dto);
        System.out.println("register = " + register);

        //when


        //then
    }

    @Test
    public void _테스트2() throws Exception{

//        PageRequestDto pageRequestDto = new PageRequestDto();
//        PageResultDto<Object[], BoardDto> list = service.getList(pageRequestDto);
//        for (BoardDto boardDto : list.getDtoList()) {
//            System.out.println("boardDto = " + boardDto);
//        }

        BoardDto boardDto = service.get(100L);
        System.out.println("boardDto = " + boardDto);
        //given


        //when


        //then
    }

    @Test
    public void _테스트03() throws Exception{
        //given
//        service.removeWithReplies(1L);

        BoardDto dto = BoardDto.builder()
                .bno(2L)
                .title("change title..")
                .content("change content..")
                .build();

        service.modify(dto);

        //when


        //then
    }




























}