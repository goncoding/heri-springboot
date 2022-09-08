package com.doosan.heritage.service;

import com.doosan.heritage.dto.replyTest.ReplyDto;
import com.doosan.heritage.repository.replyTest.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReplyServiceTest {

    @Autowired
    ReplyService service;

    @Test
    public void _테스트() throws Exception{
        //given
        List<ReplyDto> list = service.getList(75L);
        for (ReplyDto replyDto : list) {
            System.out.println("replyDto = " + replyDto);
        }

        //when


        //then
    }


}