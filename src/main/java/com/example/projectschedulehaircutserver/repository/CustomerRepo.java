package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Optional<Customer> findCustomerByAccount_UserName(String account_userName);
}
