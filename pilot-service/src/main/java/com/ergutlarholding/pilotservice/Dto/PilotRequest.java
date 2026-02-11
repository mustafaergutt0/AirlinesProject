package com.ergutlarholding.pilotservice.Dto;

import java.time.LocalDate;

public record PilotRequest(
        String name,
        String surname,
        String mail,
        String password,
        String licenseNumber,
        LocalDate birthDate
) {}