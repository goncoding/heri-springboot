package com.doosan.heritage.repository.replyTest;

import com.doosan.heritage.model.replyTest.Member01;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Member01RepositoryTest {


    @Autowired
    private Member01Repository member01Repository;

    @Test
    public void _테스트() throws Exception{
        //given
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member01 member = Member01.builder()
                    .email("user" + i + "@aaa.com")
                    .password("1111")
                    .name("user" + i)
                    .build();
            member01Repository.save(member);
        });

        //when


        //then
    }

}