package com.example.projectschedulehaircutserver.service.authentication;

import com.example.projectschedulehaircutserver.entity.Account;
import com.example.projectschedulehaircutserver.entity.Cart;
import com.example.projectschedulehaircutserver.entity.Customer;
import com.example.projectschedulehaircutserver.entity.Role;
import com.example.projectschedulehaircutserver.exeption.LoginException;
import com.example.projectschedulehaircutserver.exeption.RegisterException;
import com.example.projectschedulehaircutserver.repository.AccountRepo;
import com.example.projectschedulehaircutserver.repository.CartRepo;
import com.example.projectschedulehaircutserver.repository.CustomerRepo;
import com.example.projectschedulehaircutserver.repository.RoleRepo;
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

        String token = null;

        var customer = customerRepo.findCustomerByUsername(request.getUserName()).orElseThrow();

        token = jwtService.generateToken(customer);

        if (token == null){
            throw new LoginException("Tài Khoản Hoặc Mật Khẩu Không Chỉnh Xác");
        }

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
