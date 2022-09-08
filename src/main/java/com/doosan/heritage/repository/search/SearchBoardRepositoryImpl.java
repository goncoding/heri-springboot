package com.doosan.heritage.repository.search;

import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.model.QMember;
import com.doosan.heritage.model.replyTest.Board;
import com.doosan.heritage.model.replyTest.QBoard;
import com.doosan.heritage.model.replyTest.QMember01;
import com.doosan.heritage.model.replyTest.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search1..................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember01 member01 = QMember01.member01;

        //from
        JPQLQuery<Board> jpqlQuery = from(board);

        //join
        jpqlQuery.leftJoin(member01).on(board.writer.eq(member01));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //select, where, groupBy
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member01.email, reply.count());
        tuple.groupBy(board);

        log.info("--------------");
        log.info(jpqlQuery);
        log.info("--------------");

        List<Tuple> fetch = tuple.fetch();

        log.info(fetch);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage..................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember01 member01 = QMember01.member01;

        //from
        JPQLQuery<Board> jpqlQuery = from(board);

        //join
        jpqlQuery.leftJoin(member01).on(board.writer.eq(member01));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //select, where, groupBy
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member01.email, reply.count());

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = board.bno.goe(0L);

        if(type != null){
            String[] typeArr = type.split(" ");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member01.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;

                }
            }
            builder.and(conditionBuilder);// 전체 괄호 처리


        }
        tuple.where(builder);

        //order by
        Sort sort = pageable.getSort();
        //직접 코드로 처리하면
        //tuple.orderBy(board.bno.desc());

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(board);

        //page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();

        log.info("COUNT: " +count);

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable , count);
    }
}




















