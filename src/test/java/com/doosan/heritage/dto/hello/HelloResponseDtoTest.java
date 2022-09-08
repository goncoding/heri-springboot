package com.doosan.heritage.dto.hello;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloResponseDtoTest {

    @Test
    public void lombok_테스트() throws Exception{
        //given
        HelloResponseDto aa = new HelloResponseDto("aa", 11);

        Assertions.assertThat(aa.getName()).isEqualTo("aa");

        //when


        //then
    }

}