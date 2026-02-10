package com.ergutlarholding.airlinesmainservice.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(unique = true, nullable = false)
    private String code; // Örn: IST


    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL)
    private List<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalAirport", cascade = CascadeType.ALL)
    private List<Flight> arrivingFlights;
}