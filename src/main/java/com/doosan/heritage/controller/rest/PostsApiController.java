package com.doosan.heritage.controller.rest;

import com.doosan.heritage.dto.posts.PostsListResponseDto;
import com.doosan.heritage.dto.posts.PostsResponseDto;
import com.doosan.heritage.dto.posts.PostsSaveRequestDto;
import com.doosan.heritage.dto.posts.PostsUpdateRequestDto;
import com.doosan.heritage.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id, Model model){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public void delete(@PathVariable Long id){
         postsService.delete(id);
    }











}
