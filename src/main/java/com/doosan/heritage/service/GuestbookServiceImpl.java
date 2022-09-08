package com.doosan.heritage.service;

import com.doosan.heritage.dto.guestbook.GuestbookDto;
import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.model.Guestbook;
import com.doosan.heritage.model.QGuestbook;
import com.doosan.heritage.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;


    @Override
    public Long register(GuestbookDto dto) {

       log.info("dto........{}", dto);

        Guestbook entity = dtoToEntity(dto);
        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDto<Guestbook, GuestbookDto> getList(PageRequestDto requestDto) {

        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDto);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDto> fn = (entity -> entityToDto(entity));

//        Function<Guestbook, GuestbookDto> fn = new Function<Guestbook, GuestbookDto>() {
//            @Override
//            public GuestbookDto apply(Guestbook guestbook) {
//                GuestbookDto guestbookDto = entityToDto(guestbook);
//                System.out.println("guestbookDto = " + guestbookDto);
//                return guestbookDto;
//            }
//        };


        return new PageResultDto<>(result, fn);
    }

    @Override
    public GuestbookDto read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()) : null;

    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDto dto) {

        Optional<Guestbook> result = repository.findById(dto.getGno());
        if (result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle(dto.getTitle());
            guestbook.changeContent(dto.getContent());

            repository.save(guestbook);
        }



    }


    private BooleanBuilder getSearch(PageRequestDto requestDto){

        String type = requestDto.getType();
        String keyword = requestDto.getKeyword();

        BooleanBuilder builder = new BooleanBuilder();
        QGuestbook guestbook = QGuestbook.guestbook;

        BooleanExpression expression = guestbook.gno.gt(0L);
        builder.and(expression);

        //검색 조건이 없는 경우 return
        if(type == null || type.trim().length() == 0){
            return builder;
        }

        //검색 조건이 있는 경우
        BooleanBuilder containBuilder = new BooleanBuilder();
        if(type.contains("t")){
            containBuilder.or(guestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            containBuilder.or(guestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            containBuilder.or(guestbook.writer.contains(keyword));
        }

        builder.and(containBuilder);

        return builder;
    }

}
