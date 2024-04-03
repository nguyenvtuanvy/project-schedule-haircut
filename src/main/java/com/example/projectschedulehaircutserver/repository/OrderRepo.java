package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders, Integer> {
}
