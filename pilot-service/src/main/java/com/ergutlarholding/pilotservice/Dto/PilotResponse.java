package com.ergutlarholding.pilotservice.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PilotResponse(
        Long id,
        String name,
        String surname,
        String mail,
        String licenseNumber,
        Integer flightHours,
        BigDecimal salary, // Sistemdeki güncel maaşı
        LocalDate birthDate
) {}