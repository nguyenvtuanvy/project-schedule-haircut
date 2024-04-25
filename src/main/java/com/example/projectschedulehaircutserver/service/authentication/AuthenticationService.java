package com.example.projectschedulehaircutserver.service.authentication;

import com.example.projectschedulehaircutserver.exeption.LoginException;
import com.example.projectschedulehaircutserver.exeption.RegisterException;
import com.example.projectschedulehaircutserver.request.LoginRequest;
import com.example.projectschedulehaircutserver.request.RegisterRequest;
import com.example.projectschedulehaircutserver.response.AuthenticationResponse;

public interface AuthenticationService {
    String registerUser(RegisterRequest request) throws RegisterException;

    AuthenticationResponse authenticate(LoginRequest request) throws LoginException;
}
