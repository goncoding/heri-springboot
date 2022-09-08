package com.doosan.heritage.service;

import com.doosan.heritage.dto.guestbook.GuestbookDto;
import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.model.Guestbook;
import com.doosan.heritage.model.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface GuestbookService {

    Long register(GuestbookDto dto);

    PageResultDto<Guestbook,GuestbookDto> getList(PageRequestDto requestDto);

    GuestbookDto read(Long gno);

    void remove(Long gno);

    void modify(GuestbookDto dto);



    //영속성 컨텍스트에서 시간 등록 수정이 일어나기 때문에
    //dto -> entity 보내줄 필요가 없다..
    default Guestbook dtoToEntity(GuestbookDto dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    //하지만 entity에서는 dto로 시간을 보내줘야한다.
    default GuestbookDto entityToDto(Guestbook entity){
        GuestbookDto dto = GuestbookDto.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }

}
