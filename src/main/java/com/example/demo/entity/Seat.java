package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

/** Physical seat in a screen. Created once, reused by every show. */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"screen_id", "rowLabel", "seatNumber"}))
@Getter @Setter @NoArgsConstructor
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String rowLabel;      // "A"
    @Column(nullable = false)
    private Integer seatNumber;   // 5  -> displayed as "A5"

    @Enumerated(EnumType.STRING)
    private SeatType seatType = SeatType.REGULAR;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    public String getLabel() { return rowLabel + seatNumber; }
}