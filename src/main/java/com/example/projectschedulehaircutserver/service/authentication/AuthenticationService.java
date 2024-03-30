package com.example.projectschedulehaircutserver.service.authentication;

import com.example.projectschedulehaircutserver.request.RegisterRequest;
import com.example.projectschedulehaircutserver.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);

    AuthenticationResponse authenticate(RegisterRequest registerRequest);
}
