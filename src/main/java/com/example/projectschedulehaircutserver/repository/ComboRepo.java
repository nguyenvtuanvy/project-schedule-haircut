package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.dto.ComboDTO;
import com.example.projectschedulehaircutserver.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ComboRepo extends JpaRepository<Combo, Integer> {
    @Query("select c from Combo c where c.id = :id")
    Combo findComboById(@Param("id") Integer id);

    @Query("select new com.example.projectschedulehaircutserver.dto.ComboDTO(c.id, c.name, c.price, c.haircutTime) from Combo c")
    Set<ComboDTO> findAllCombo();
}
