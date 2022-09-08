package com.doosan.heritage.service;

import com.doosan.heritage.dto.replyTest.ReplyDto;
import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.model.replyTest.Reply;
import com.doosan.heritage.repository.replyTest.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    
    @Override
    public Long register(ReplyDto replyDto) {

        Reply reply = dtoToEntity(replyDto);
        replyRepository.save(reply);

        return replyDto.getRno();
    }

    @Override
    public List<ReplyDto> getList(Long bno) {

        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDto replyDto) {

        Reply reply = dtoToEntity(replyDto);
        replyRepository.save(reply);

    }

    @Override
    public void remove(Long rno) {

        replyRepository.deleteById(rno);

    }
}
