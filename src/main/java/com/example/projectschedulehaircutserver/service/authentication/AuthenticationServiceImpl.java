package com.example.projectschedulehaircutserver.service.authentication;

import com.example.projectschedulehaircutserver.entity.Account;
import com.example.projectschedulehaircutserver.entity.Cart;
import com.example.projectschedulehaircutserver.entity.Customer;
import com.example.projectschedulehaircutserver.entity.Role;
import com.example.projectschedulehaircutserver.exeption.LoginException;
import com.example.projectschedulehaircutserver.exeption.RegisterException;
import com.example.projectschedulehaircutserver.repository.*;
import com.example.projectschedulehaircutserver.request.LoginRequest;
import com.example.projectschedulehaircutserver.request.RegisterRequest;
import com.example.projectschedulehaircutserver.response.AuthenticationResponse;
import com.example.projectschedulehaircutserver.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final CustomerRepo customerRepo;
    private final CartRepo cartRepo;
    private final PasswordEncoder encoder;
    private final RoleRepo roleRepo;
    private final AccountRepo accountRepo;
    private final EmployeeRepo employeeRepo;
    private final ManagerRepo managerRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public String registerUser(RegisterRequest request) throws RegisterException {
            Role role = roleRepo.findById(2).orElseThrow(() -> new RuntimeException("No roles specified."));
            if (request.getUserName() != null){
                var customer = customerRepo.findCustomerByAccount_UserName(request.getUserName());
                if (customer.isPresent()){
                    throw new RegisterException("UserName Đã Được Sử Dụng");
                }
            }

            if(request.getPhone() != null){
                var customer = customerRepo.findCustomerByPhone(request.getPhone());
                if (customer.isPresent()){
                    throw new RegisterException("Số Điện Thoại Đã Được Sử Dụng");
                }
            }

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

            Cart cart = Cart.builder()
                    .customer(customer)
                    .build();

            try {
                customerRepo.save(customer);
                cartRepo.save(cart);
                return "Đăng Kí Thành Công";
            } catch (Exception e){
                throw new RegisterException(e.getMessage());
            }

    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) throws LoginException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final Object[] accountHolder = new Object[1];

        authentication.getAuthorities().forEach(grantedAuthority -> {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                accountHolder[0] = customerRepo.findCustomerByAccount_UserName(request.getUserName()).orElseThrow();
            } else if (grantedAuthority.getAuthority().equals("ROLE_EMPLOYEE")) {
                accountHolder[0] = employeeRepo.findEmployeeByAccount_UserName(request.getUserName()).orElseThrow();
            } else if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
                accountHolder[0] = managerRepo.findManagerByUseName(request.getUserName());
            }
        });

        var account = accountHolder[0];
        if (account == null) {
            throw new LoginException("Unable to find account with the provided username");
        }

        String token = jwtService.generateToken((UserDetails) account);

        if (token == null) {
            throw new LoginException("Unable to generate token, please try again");
        }

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
