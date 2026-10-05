package com.example.demo.repository;

import com.example.demo.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    // Powers the "theaters + showtimes for this movie in my city on this date" page
    @Query("""
           SELECT s FROM Show s
             JOIN FETCH s.screen sc
             JOIN FETCH sc.theater t
           WHERE s.movie.id = :movieId
             AND t.city.id = :cityId
             AND s.startTime BETWEEN :from AND :to
             AND s.active = true
           ORDER BY t.name, s.startTime
           """)
    List<Show> findShowsForMovieInCity(@Param("movieId") Integer movieId,
                                       @Param("cityId") Integer cityId,
                                       @Param("from") LocalDateTime from,
                                       @Param("to") LocalDateTime to);

    // Overlap check when an admin schedules a new show
    @Query("""
           SELECT COUNT(s) > 0 FROM Show s
           WHERE s.screen.id = :screenId AND s.active = true
             AND s.startTime < :end AND s.endTime > :start
           """)
    boolean existsOverlappingShow(@Param("screenId") Integer screenId,
                                  @Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end);
}