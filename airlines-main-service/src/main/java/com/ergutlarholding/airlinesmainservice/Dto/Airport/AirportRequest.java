package com.ergutlarholding.airlinesmainservice.Dto.Airport;

public record AirportRequest(
        String name,
        String city,
        String country,
        String code // IST, SAW, JFK gibi
) {}