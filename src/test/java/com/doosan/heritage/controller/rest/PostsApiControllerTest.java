package com.doosan.heritage.controller.rest;

import com.doosan.heritage.dto.posts.PostsSaveRequestDto;
import com.doosan.heritage.model.Posts;
import com.doosan.heritage.repository.PostsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void clearup(){
        postsRepository.deleteAll();
    }

    @Test
    public void _테스트() throws Exception{
        //given
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title("title")
                .content("content..")
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);


    }

    @Test
    public void Posts_수정된다() throws Exception{
        //given
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title("title")
                .content("content..")
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);


    }

    @Test
    public void baseTime() throws Exception{
        //given
        //given
        Posts save = postsRepository.save(Posts.builder()
                .title("title..1")
                .content("content...")
                .author("gon@naver.com")
                .build());

        //when
        List<Posts> all = postsRepository.findAll();

        //then
        Posts posts = all.get(0);
        System.out.println("posts.getCreatedDate() = " + posts.getCreatedDate());
        System.out.println("posts.getModifiedDate() = " + posts.getModifiedDate());

        Assertions.assertThat(LocalDateTime.now()).isAfter(posts.getCreatedDate());

    }




}


















