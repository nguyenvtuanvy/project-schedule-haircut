package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponsRepo extends JpaRepository<Coupons, Integer> {
}
