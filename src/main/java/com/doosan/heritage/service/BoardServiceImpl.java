package com.doosan.heritage.service;

import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.dto.replyTest.BoardDto;
import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.model.replyTest.Member01;
import com.doosan.heritage.repository.replyTest.BoardRepository;
import com.doosan.heritage.repository.replyTest.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDto dto) {

        Board board = dtoToEntity(dto);

        System.out.println("board = " + board);

        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDto<Object[], BoardDto> getList(PageRequestDto pageRequestDto) {

        Pageable pageable = pageRequestDto.getPageable(Sort.by("bno").descending());

        Function<Object[], BoardDto> fn = (en -> entityToDto((Board) en[0], (Member01) en[1], (Long) en[2]));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

//        Page<Object[]> result = boardRepository.searchPage(
//                pageRequestDto.getType(),
//                pageRequestDto.getKeyword(),
//                pageable
//        );

        return new PageResultDto<>(result,fn);
    }

    @Override
    public BoardDto get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDto((Board) arr[0], (Member01) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        //1. 댓글 부터 삭제
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDto boardDto) {

        Optional<Board> byId = boardRepository.findById(boardDto.getBno());
        if (byId.isPresent()) {
            Board board = byId.get();
            board.changeTitle(boardDto.getTitle());
            board.changeContent(boardDto.getContent());

            boardRepository.save(board);
        }


    }
}
