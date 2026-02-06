package com.ergutlarholding.airlinesproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "planes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planeId;

    @Column(nullable = false)
    private String planeName;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer totalSeatCount;

    // Her uçağın şu an bulunduğu tek bir lokasyon (havalimanı) vardır.
    @OneToOne
    @JoinColumn(name = "current_airport_id", referencedColumnName = "id")
    private Airport currentAirport;

    // Plane.java içine eklenecekler:
    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL)
    private List<Flight> flights;
}