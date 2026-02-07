package com.ergutlarholding.airlinesproject.Dto.Pilot;

import java.time.LocalDate;

public record PilotRequest(
        String name,
        String surname,
        String mail,
        String password,
        String licenseNumber,
        LocalDate birthDate
) {}