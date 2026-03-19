package com.ergutlarholding.authservice.Entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // Auth-DB içindeki tablo ismi
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(nullable = false)
    private String password; // Bu alan BCrypt ile şifrelenmiş tutulacak

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}