package com.doosan.heritage.repository.replyTest;

import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.model.replyTest.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void _테스트() throws Exception{
        //given
        IntStream.rangeClosed(1,300).forEach(i -> {

            long bno = (long) (Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("reply..." + i)
                    .board(board)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);

        });


        //when


        //then
    }

    @Test
    public void _테스트02() throws Exception{

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());

        replyList.forEach(reply -> System.out.println("reply = " + reply));


        //when
        
        
        //then
    }
    

}