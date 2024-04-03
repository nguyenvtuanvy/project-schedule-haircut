package com.example.projectschedulehaircutserver.repository;

import com.example.projectschedulehaircutserver.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServiceRepo extends JpaRepository<Service, Integer> {
    @Query("select s from Service s where s.id = :id")
    Optional<Service> findServiceById(@Param("id") Integer id);
}
