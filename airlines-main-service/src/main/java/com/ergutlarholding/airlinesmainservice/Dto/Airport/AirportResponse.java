package com.ergutlarholding.airlinesmainservice.Dto.Airport;

public record AirportResponse(
        Long id,
        String name,
        String city,
        String country,
        String code
) {}