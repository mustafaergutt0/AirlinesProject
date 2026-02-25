package com.ergutlarholding.airlinesmainservice.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "passengers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auth_id", unique = true, nullable = false)
    private Long authId; // Auth-Service ile olan bağlantımız

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false, length = 11)
    private String tckn;

    private String gender;
    private LocalDate birthDate;
    private String gsm;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}