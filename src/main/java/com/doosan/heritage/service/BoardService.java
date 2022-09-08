package com.doosan.heritage.service;

import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.dto.replyTest.BoardDto;
import com.doosan.heritage.model.Member;
import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.model.replyTest.Member01;

public interface BoardService {

    Long register(BoardDto dto);

    PageResultDto<Object[],BoardDto> getList(PageRequestDto pageRequestDto);

    BoardDto get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDto boardDto);

    default Board dtoToEntity(BoardDto dto){

        Member01 member = Member01.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }


    default  BoardDto entityToDto(Board board, Member01 member, Long replyCount){
        BoardDto dto = BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getCreatedDate())
                .modeDate(board.getModifiedDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();

        return dto;
    }

}
