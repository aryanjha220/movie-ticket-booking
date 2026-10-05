package com.example.demo.repository;

import com.example.demo.entity.ShowSeat;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {

    // Seat map for the UI
    @Query("SELECT ss FROM ShowSeat ss JOIN FETCH ss.seat WHERE ss.show.id = :showId " +
            "ORDER BY ss.seat.rowLabel, ss.seat.seatNumber")
    List<ShowSeat> findSeatMap(@Param("showId") Integer showId);

    // Your pessimistic lock, upgraded:
    //  - ORDER BY id => every transaction locks rows in the same order => no deadlocks
    //  - lock timeout => fail fast (3s) instead of hanging forever
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @Query("SELECT ss FROM ShowSeat ss WHERE ss.id IN :ids ORDER BY ss.id")
    List<ShowSeat> findByIdInForUpdate(@Param("ids") List<Integer> ids);

    // Used by the expiry job (Phase 3): one bulk UPDATE, no loading rows into memory
    @Modifying
    @Query("""
           UPDATE ShowSeat ss
           SET ss.status = com.example.demo.entity.SeatStatus.AVAILABLE,
               ss.holdExpiresAt = null, ss.heldBy = null, ss.booking = null
           WHERE ss.status = com.example.demo.entity.SeatStatus.HELD
             AND ss.holdExpiresAt < :now
           """)
    int releaseExpiredHolds(@Param("now") LocalDateTime now);
}