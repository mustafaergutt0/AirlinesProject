package com.ergutlarholding.airlinesproject.Dto.Passenger;

import java.time.LocalDate;

public record PassengerResponse(
        Long id,          // Veritabanındaki otomatik ID
        String name,
        String surname,
        String tckn,
        String mail,
        String gsm,
        LocalDate birthDate
) {}