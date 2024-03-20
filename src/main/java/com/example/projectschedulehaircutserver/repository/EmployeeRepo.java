package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Optional<Employee> findEmployeeByAccount_UserName(String account_userName);
}
