package com.doosan.heritage.repository;

import com.doosan.heritage.model.Guestbook;
import com.doosan.heritage.model.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRespositoryTest {

    @Autowired
    private GuestbookRepository guestbookRespository;

    @Test
    public void _테스트() throws Exception{
        //given
        IntStream.rangeClosed(1,300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Guestbook save = guestbookRespository.save(guestbook);
            System.out.println("save = " + save);
        });

        //when


        //then
    }

    @Test
    @Commit
    @Transactional
    public void _테스트02() throws Exception{

        Optional<Guestbook> result = guestbookRespository.findById(300L);

        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle("changed title.....12");
            guestbook.changeContent("changed content...12");
            guestbookRespository.save(guestbook);

//        Assertions.assertThat(guestbook.getContent()).isEqualTo("changed content...");
        }


    }

    @Test
    public void 해당_테스트() throws Exception{
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook guestbook = QGuestbook.guestbook;

        String keyword = "1";

        //조건 컨테이너
        BooleanBuilder builder = new BooleanBuilder();

        //컨테이너에 들어갈 조건 표현식
        BooleanExpression exTitle = guestbook.title.contains(keyword);
        BooleanExpression exContent = guestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);


        builder.and(exAll);
        builder.and(guestbook.gno.gt(0L));

        Page<Guestbook> all = guestbookRespository.findAll(builder, pageable);



        all.stream().forEach(guestbook1 -> {
            System.out.println("guestbook1 = " + guestbook1);
        });


        //when


        //then
    }





}