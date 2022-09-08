package com.doosan.heritage.controller.view;

import com.doosan.heritage.dto.guestbook.GuestbookDto;
import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.dto.replyTest.BoardDto;
import com.doosan.heritage.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(PageRequestDto pageRequestDto, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto){

        System.out.println("pageRequestDto = " + pageRequestDto);

        PageResultDto<Object[], BoardDto> list = boardService.getList(requestDto);

        model.addAttribute("result",boardService.getList(requestDto));

        return "/page/kr/test/board_list";
    }

    @GetMapping("/register")
    public String register(){
        return "/page/kr/test/board_register";
    }

    @PostMapping("/register")
    public String registerPost(BoardDto dto, RedirectAttributes redirectAttributes){

        Long register = boardService.register(dto);

        redirectAttributes.addFlashAttribute("msg",register);

        return "redirect:/board/board_list";
    }

    @GetMapping("/read")
    public String read(Long bno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model){

        BoardDto read = boardService.get(bno);
        model.addAttribute("dto",read);

        return "/page/kr/test/board_read";
    }

    @PostMapping("/remove")
    public String delete(long bno, @ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes redirectAttributes){

        System.out.println("delete .... gno = " + bno);

        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }


    @GetMapping("/modify")
    public String modify(long bno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model){

        BoardDto read = boardService.get(bno);
        model.addAttribute("dto",read);

        return "/page/kr/test/board_modify";
    }

    @PostMapping("/modify")
    public String modifyPost(BoardDto dto, @ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes redirectAttributes){

        boardService.modify(dto);

        redirectAttributes.addAttribute("page",requestDto.getPage());
        redirectAttributes.addAttribute("bno",dto.getBno());
        redirectAttributes.addAttribute("type",requestDto.getType());
        redirectAttributes.addAttribute("keyword",requestDto.getKeyword());

        return "redirect:/board/read";
    }


}










