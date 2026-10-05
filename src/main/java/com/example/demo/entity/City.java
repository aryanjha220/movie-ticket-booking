package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "district"}))
@Getter @Setter @NoArgsConstructor
public class City {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;       // "Prayagraj"
    private String district;   // "Prayagraj"
    private String state;      // "Uttar Pradesh"
}