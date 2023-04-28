package com.example.moviereview.controller;

import com.example.moviereview.Service.MovieService;
import com.example.moviereview.dto.MovieDTO;
import com.example.moviereview.dto.PageRequestDTO;
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
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO : {}",pageRequestDTO);
        model.addAttribute("result",movieService.getList(pageRequestDTO));

        return "/movie/list";
    }

    @GetMapping("/register")
    public String register(){
        return "/movie/register";
    }
    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes){
        log.info("save ----------------------------------------");
        log.info("movieDTO : {}", movieDTO);
        Long mno = movieService.register(movieDTO);
        log.info("mno : {}",mno);
        redirectAttributes.addAttribute("msg",23);
        return "redirect:/movie/list";
    }

    @GetMapping({"/read", "/modify"})
    public String read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,Model model){
        log.info("mno : {}",mno);
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);

        return "/movie/read";
    }



}
