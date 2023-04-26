package com.example.moviereview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LayoutController {

    @GetMapping("/test")
    public String layout(){
        return "movie/register";
    }
}
