package com.example.projectschedulehaircutserver.service.authentication;

import com.example.projectschedulehaircutserver.entity.Account;
import com.example.projectschedulehaircutserver.entity.Customer;
import com.example.projectschedulehaircutserver.entity.Role;
import com.example.projectschedulehaircutserver.repository.AccountRepo;
import com.example.projectschedulehaircutserver.repository.CustomerRepo;
import com.example.projectschedulehaircutserver.repository.RoleRepo;
import com.example.projectschedulehaircutserver.request.RegisterRequest;
import com.example.projectschedulehaircutserver.response.AuthenticationResponse;
import com.example.projectschedulehaircutserver.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final CustomerRepo customerRepo;
    private final PasswordEncoder encoder;
    private final RoleRepo roleRepo;
    private final AccountRepo accountRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponse register(RegisterRequest request) {
            Role role = roleRepo.findById(2).orElseThrow(() -> new RuntimeException("No roles specified."));

            Account account = Account.builder()
                    .fullName(request.getFullName())
                    .userName(request.getUserName())
                    .password(encoder.encode(request.getPassword()))
                    .age(request.getAge())
                    .address(request.getAddress())
                    .role(role)
                    .phone(request.getPhone())
                    .build();

            Account savedAccount = accountRepo.save(account);

            Customer customer = new Customer();
            customer.setFullName(request.getFullName());
            customer.setUserName(request.getUserName());
            customer.setPassword(encoder.encode(request.getPassword()));
            customer.setRole(role);
            customer.setAge(request.getAge());
            customer.setAddress(request.getAddress());
            customer.setPhone(request.getPhone());
            customer.setIsBlocked(false);
            customer.setAccount(savedAccount);

            customerRepo.save(customer);

            var jwtToken = jwtService.generateToken(customer);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
    }

    @Override
    public AuthenticationResponse authenticate(RegisterRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var customer = customerRepo.findCustomerByAccount_UserName(request.getUserName()).orElseThrow();

        var jwtToken = jwtService.generateToken(customer);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
