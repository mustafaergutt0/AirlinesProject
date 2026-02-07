package com.ergutlarholding.airlinesproject.Dto.Plane;

public record PlaneResponse(
        Long planeId,
        String planeName,
        String model,
        Integer totalSeatCount,
        String currentAirportName
) {}