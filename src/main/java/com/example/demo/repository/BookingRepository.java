package com.example.demo.repository;

import com.example.demo.entity.Booking;
import com.example.demo.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByBookingRef(String bookingRef);
    List<Booking> findByUserIdOrderByCreatedAtDesc(Integer userId);
    List<Booking> findByStatusAndExpiresAtBefore(BookingStatus status, LocalDateTime now);
}