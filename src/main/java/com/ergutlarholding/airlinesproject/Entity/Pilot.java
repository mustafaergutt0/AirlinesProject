package com.ergutlarholding.airlinesproject.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(unique = true, nullable = false)
    private String mail; // Giriş yapabilmesi için

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String licenseNumber;


    @Builder.Default
    private Integer flightHours=0;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    private LocalDate birthDate;

    // "Bir pilotun bir sürü uçuşu olabilir"
    @OneToMany(mappedBy = "pilot", cascade = CascadeType.ALL)
    private List<Flight> flights;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}