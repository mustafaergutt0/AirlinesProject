package com.ergutlarholding.airlinesproject.Dto.Flight;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightRequest(
        String flightCode,
        BigDecimal price,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        Long pilotId,
        Long planeId,
        Long departureAirportId,
        Long arrivalAirportId
) {}