package com.ergutlarholding.airlinesproject.Dto.Airport;

public record AirportResponse(
        Long id,
        String name,
        String city,
        String country,
        String code
) {}