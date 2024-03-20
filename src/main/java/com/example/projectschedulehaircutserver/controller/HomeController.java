package com.example.projectschedulehaircutserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    @GetMapping("")
    public String getText(){
        return "home";
    }
}
