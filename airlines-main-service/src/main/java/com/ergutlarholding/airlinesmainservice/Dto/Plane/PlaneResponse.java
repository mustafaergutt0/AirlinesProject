package com.ergutlarholding.airlinesmainservice.Dto.Plane;

public record PlaneResponse(
        Long planeId,
        String planeName,
        String model,
        Integer totalSeatCount,
        String currentAirportName
) {}