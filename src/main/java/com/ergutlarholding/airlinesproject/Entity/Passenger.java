package com.ergutlarholding.airlinesproject.Entity;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false, length = 11)
    private String tckn; // TC Kimlik No

    private String gender;

    private LocalDate birthDate;

    @Column(unique = true)
    private String mail;

    private String gsm;

    // Passenger.java içine eklenecekler:
    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}