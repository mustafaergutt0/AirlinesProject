package com.ergutlarholding.authservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    // Auth-Service'in kullanacağı alanlar
    private String mail;
    private String password;
    private String role; // "PILOT" veya "PASSENGER"

    // Diğer servislere paslanacak ortak alanlar
    private String name;
    private String surname;
    private LocalDate birthDate;

    // Sadece Passenger (Yolcu) ise dolacak alanlar
    private String tckn;
    private String gsm;
    private String gender;

    // Sadece Pilot ise dolacak alanlar
    private String licenseNumber;
}

