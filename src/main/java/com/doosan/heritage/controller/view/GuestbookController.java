package com.doosan.heritage.controller.view;

import com.doosan.heritage.dto.guestbook.GuestbookDto;
import com.doosan.heritage.dto.page.PageRequestDto;
import com.doosan.heritage.dto.page.PageResultDto;
import com.doosan.heritage.model.Guestbook;
import com.doosan.heritage.service.GuestbookService;
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
@RequestMapping("/guestbook")
@RequiredArgsConstructor
@Log4j2
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDto pageRequestDto, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto){
        log.info("list.....");
        PageResultDto<Guestbook, GuestbookDto> list = service.getList(pageRequestDto);

        model.addAttribute("result", list);
        return "/page/kr/test/list";
    }

    @GetMapping("/register")
    public String register(Model model){
        log.info("register get.....");
//        model.addAttribute("guestbookDto", new GuestbookDto());
        return "/page/kr/test/register";
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDto dto, RedirectAttributes redirectAttributes){
        System.out.println("dto = " + dto.getContent());
        log.info("register post.....");

        Long savedGno = service.register(dto);
        System.out.println("savedGno = " + savedGno);
        redirectAttributes.addFlashAttribute("msg", savedGno);

        return "redirect:/guestbook/list";

    }

    @GetMapping("/read")
    public String read(long gno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model){

        GuestbookDto read = service.read(gno);
        model.addAttribute("dto",read);

        return "/page/kr/test/read";
    }

    @GetMapping("/modify")
    public String modify(long gno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model){

        GuestbookDto read = service.read(gno);
        model.addAttribute("dto",read);
        model.addAttribute("page",requestDto.getPage());

        return "/page/kr/test/modify";
    }

    @PostMapping("/remove")
    public String delete(long gno, @ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes redirectAttributes){

        System.out.println("delete .... gno = " + gno);

        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modifyPost(GuestbookDto dto, @ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes redirectAttributes){

        service.modify(dto);

        redirectAttributes.addAttribute("page",requestDto.getPage());
        redirectAttributes.addAttribute("gno",dto.getGno());
        redirectAttributes.addAttribute("type",requestDto.getType());
        redirectAttributes.addAttribute("keyword",requestDto.getKeyword());

        return "redirect:/guestbook/read";
    }





}
