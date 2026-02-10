package com.ergutlarholding.airlinesmainservice.Dto.Passenger;

import java.time.LocalDate;

/**
 * Yolcu oluşturma isteği için kullanılan veri taşıma nesnesi.
 */
public record PassengerRequest(
        String name,
        String surname,
        String tckn,      // TC Kimlik No
        String mail,
        String password,
        String gsm,       // Telefon
        String gender,    // Cinsiyet
        LocalDate birthDate
) {}