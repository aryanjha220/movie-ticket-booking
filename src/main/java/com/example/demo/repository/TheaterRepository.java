package com.example.demo.repository;

import com.example.demo.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByCityId(Integer cityId);
}