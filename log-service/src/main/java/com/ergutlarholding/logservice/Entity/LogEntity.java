package com.ergutlarholding.logservice.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String serviceName; // Hangi servis (Airlines, Pilot vb.)
    private String message;     // Log içeriği

    private String timestamp;   // İşlem zamanı

}