package com.example.projectschedulehaircutserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class UserController {
    @GetMapping("/test")
    public ResponseEntity<String> getText(){
        return ResponseEntity.ok("Hello Word Customer!!!");
    }
}
