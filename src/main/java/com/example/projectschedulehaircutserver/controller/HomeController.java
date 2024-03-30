package com.example.projectschedulehaircutserver.controller;

import com.example.projectschedulehaircutserver.request.RegisterRequest;
import com.example.projectschedulehaircutserver.response.AuthenticationResponse;
import com.example.projectschedulehaircutserver.service.authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final AuthenticationService authenticationService;
    @GetMapping("/demo")
    public ResponseEntity<String> getText(){

        return ResponseEntity.ok("Hello Word!!!");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.authenticate(registerRequest));
    }
}
