package com.ergutlarholding.airlinesproject.Dto.Flight;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightResponse(
        Long id,
        String flightCode,
        BigDecimal price,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String pilotName,
        String planeName,
        String departureAirportName,
        String arrivalAirportName
) {}