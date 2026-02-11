package com.ergutlarholding.airlinesmainservice.Dto.Flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightResponse(
        Long id,
        String flightCode,
        BigDecimal price,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        Long pilotId, // String pilotName yerine Long pilotId geldi
        String planeName,
        String departureAirportName,
        String arrivalAirportName
) {}