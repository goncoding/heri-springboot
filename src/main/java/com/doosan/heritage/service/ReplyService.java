package com.doosan.heritage.service;

import com.doosan.heritage.dto.replyTest.ReplyDto;
import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.model.replyTest.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDto replyDto);

    List<ReplyDto> getList(Long bno);

    void modify(ReplyDto replyDto);

    void remove(Long rno);

    //dto -> reply
    default Reply dtoToEntity(ReplyDto dto){

        Board board = Board.builder().bno(dto.getBno()).build();

        Reply reply = Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();

        return reply;
    }

    //entity -> dto
    default  ReplyDto entityToDto(Reply entity){

        ReplyDto dto = ReplyDto.builder()
                .rno(entity.getRno())
                .text(entity.getText())
                .replyer(entity.getReplyer())
                .regDate(entity.getCreatedDate())
                .modDate(entity.getModifiedDate())
                .build();

        return dto;
    }



}
