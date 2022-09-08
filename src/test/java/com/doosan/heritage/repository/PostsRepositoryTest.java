package com.doosan.heritage.repository;

import com.doosan.heritage.model.Posts;
import com.doosan.heritage.model.QPosts;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void _테스트() throws Exception{
        //given
        Posts save = postsRepository.save(Posts.builder()
                .title("title..1")
                .content("content...")
                .author("gon@naver.com")
                .build());

        Posts all = postsRepository.findAll().get(0);

        Assertions.assertThat(all.getTitle()).isEqualTo(save.getTitle());


        //when


        //then
    }

    @Test
    public void dsl_테스트() throws Exception{
        Posts posts = new Posts();
        em.persist(posts);

        JPAQueryFactory query = new JPAQueryFactory(em);

        QPosts qPosts = QPosts.posts;

        Posts posts1 = query.selectFrom(qPosts).fetchOne();

        Assertions.assertThat(posts1).isEqualTo(posts);

    }




}