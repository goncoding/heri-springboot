package com.doosan.heritage.repository.replyTest;


import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.model.replyTest.Member01;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardRepositoryTest {

    @Test
    public void _테스트33() throws Exception{
        //given
        boardRepository.search1();

        //when


        //then
    }


    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void _테스트() throws Exception{
        //given
        IntStream.rangeClosed(1,100).forEach(i -> {

            Member01 member =
                    Member01.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..."+i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });

        //when


        //then
    }

    @Test
    @Transactional
    public void _테스트2() throws Exception{
        //given
//        Optional<Board> result = sboardRepository.findById(100L);

//        Board board = result.get();
//
//        System.out.println("board = " + board);
//        System.out.println("board.getWriter() = " + board.getWriter());


        //when


        //then
    }

    @Test
    public void _테스트03() throws Exception{
        //given
//        Object result = boardRepository.getBoardWithWriter(100L);

//        List<Object[]> result = boardRepository.getBoardWithReply(100L);
//
//        for (Object[] arr : result) {
//            String s = Arrays.toString(arr);
//            System.out.println("s = " + s);
//        }

//        for (Object[] arr : result) {
//            for (Object o : arr) {
//                System.out.println("o = " + o);
//            }
//        }

//
//        Object[] arr = (Object[]) result;
//
//        String s = Arrays.toString(arr);
//        System.out.println("s = " + s);

//        for (Object o : arr) {
//            System.out.println("o = " + o);
//        }

//        System.out.println("result = " + result);

//        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
//
//        Page<Object[]> result01 = boardRepository.getBoardWithReplyCount(pageable);
//
//        for (Object[] objects : result01) {
//            String s = Arrays.toString(objects);
//            System.out.println("s = " + s);
//        }

        Object boardByBno = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) boardByBno;

        String s = Arrays.toString(arr);
        System.out.println("s = " + s);

//        System.out.println("boardByBno = " + boardByBno);


        //when


        //then
    }

@Test
public void _테스트22() throws Exception{
    //given
    Pageable pageable = PageRequest.of(0, 10,Sort.by("bno").descending()
            .and(Sort.by("title").ascending()));

    Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);

    //when




    //then
}


}