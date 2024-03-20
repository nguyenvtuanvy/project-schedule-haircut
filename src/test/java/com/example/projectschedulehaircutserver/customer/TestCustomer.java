package com.example.projectschedulehaircutserver.customer;

import com.example.projectschedulehaircutserver.dto.CustomerDTO;
import com.example.projectschedulehaircutserver.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCustomer {
    @Autowired
    private CustomerService customerService;

    @Test
    void create(){
        CustomerDTO customerDTO = new CustomerDTO("hieu3110", "12345", "nguyễn văn hiếu", 30, "Vĩnh Điện, Quảng Nam", "0327443353");
        customerService.createCustomer(customerDTO, 2);
    }
}
