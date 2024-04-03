package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComboRepo extends JpaRepository<Combo, Integer> {
    @Query("select c from Combo c where c.id = :id")
    Optional<Combo> findComboById(@Param("id") Integer id);
}
