package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponsRepo extends JpaRepository<Coupons, Integer> {
    @Query(value = "CALL findCouponByCustomerId(:customerId)", nativeQuery = true)
    Optional<Coupons> findCouponByCustomerId(@Param("customerId") Integer customerId);

    @Query("select c from Coupons c where c.isBlocked = false and DATE(c.expiry) = CURDATE()")
    List<Coupons> findCouponsByExpiryAndIsBlocked();
}
