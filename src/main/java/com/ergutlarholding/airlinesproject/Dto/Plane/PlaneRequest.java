package com.ergutlarholding.airlinesproject.Dto.Plane;

public record PlaneRequest(
        String planeName,
        String model,
        Integer totalSeatCount,
        Long currentAirportId // Uçağın bağlı olduğu havalimanı ID'si
) {}