package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_show",
        indexes = @Index(name = "idx_show_screen_start", columnList = "screen_id, startTime"))
@Getter @Setter @NoArgsConstructor
public class Show {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(nullable = false)
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(precision = 10, scale = 2)
    private BigDecimal basePrice;
    private String format;        // "2D", "3D", "IMAX"
    private boolean active = true;
}