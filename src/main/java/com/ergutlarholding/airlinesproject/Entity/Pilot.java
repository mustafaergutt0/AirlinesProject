package com.ergutlarholding.airlinesproject.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pilots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pilot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    private LocalDate birthDate;

    // Pilot.java içine eklenecekler:
    @OneToMany(mappedBy = "pilot", cascade = CascadeType.ALL)
    private List<Flight> flights;
}