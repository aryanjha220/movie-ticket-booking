package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;
    private String language;
    private String genre;
    private Integer durationMinutes;
    @Column(length = 2000)
    private String description;
    private String posterUrl;
    private LocalDate releaseDate;
    private boolean active = true;
}