package com.doosan.heritage.controller.view;

import com.doosan.heritage.dto.posts.PostsListResponseDto;
import com.doosan.heritage.dto.posts.PostsResponseDto;
import com.doosan.heritage.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kr/posts")
public class PostsController {

    private final PostsService service;

    @GetMapping("/all")
    public String findAll(Model model){

        List<PostsListResponseDto> dtoList = service.findAllDesc();
        model.addAttribute("posts",dtoList);

        return "/page/kr/test-index";
    }

    @GetMapping("/save")
    public String save(Model model){

        return "/page/kr/test-save";
    }



    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){

        PostsResponseDto dto = service.findById(id);
        model.addAttribute("post",dto);

        return "/page/kr/test-update";
    }

//    @GetMapping("/read")
//  s  public String read(Model model, Long id){
//
//        PostsResponseDto dto = service.findById(id);
//        model.addAttribute("ports",dto);
//        return "/page/kr/test-update";
//    }




}
