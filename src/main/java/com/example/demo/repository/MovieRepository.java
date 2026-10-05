package com.example.demo.repository;

import com.example.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByActiveTrue();

    // Movies that have at least one upcoming show in a given city
    @Query("""
           SELECT DISTINCT s.movie FROM Show s
           WHERE s.screen.theater.city.id = :cityId
             AND s.startTime > :now
             AND s.active = true
             AND s.movie.active = true
           """)
    List<Movie> findNowShowingInCity(@Param("cityId") Integer cityId,
                                     @Param("now") LocalDateTime now);
}