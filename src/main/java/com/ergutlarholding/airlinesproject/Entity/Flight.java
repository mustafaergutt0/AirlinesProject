package com.ergutlarholding.airlinesproject.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flightCode; // Örn: TK1903

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime departureTime; // Şemadaki DestinationTime (Kalkış)

    @Column(nullable = false)
    private LocalDateTime arrivalTime; // Varış Zamanı

    // Foreign Key: Uçuşu gerçekleştiren Pilot
    @ManyToOne
    @JoinColumn(name = "pilot_id", referencedColumnName = "id")
    private Pilot pilot;

    // Foreign Key: Uçuşta kullanılan Uçak
    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "planeId")
    private Plane plane;

    // Foreign Key: Kalkış Havalimanı
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    private Airport departureAirport;

    // Foreign Key: Varış Havalimanı
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "id")
    private Airport arrivalAirport;

    // Flight.java içine eklenecekler:
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}