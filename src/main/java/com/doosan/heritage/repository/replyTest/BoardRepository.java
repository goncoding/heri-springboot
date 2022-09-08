package com.doosan.heritage.repository.replyTest;

import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository {

    //한개의 로우(object) 내에 object[]로 나옴
    @Query("select b, w from Board b left join b.writer w where b.bno=:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);


    //board에서 reply를 가는 부분이 지정이 되어있지 않아서 on 절을 작성해줘야한다..
    @Query("select b,r from Board b left join Reply r " +
            "on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);


    @Query(value = "select b,w,count(r.rno) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "group by b",
    countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);


    @Query( "select b,w,count(r.rno) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);



}
