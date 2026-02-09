package com.ergutlarholding.airlinesproject.Dto.Ticket;

import lombok.Builder;

@Builder
public record TicketResponse(
        Long id,
        String seatNumber,
        String ticketClass,
        String passengerName,       // Yolcu ID yerine adı
        String flightCode,          // Uçuş ID yerine kodu (TK1903 vb.)
        String departureAirport,    // Kalkış yeri ismi
        String arrivalAirport,      // Varış yeri ismi
        java.math.BigDecimal price  // Biletin fiyatı (Flight'tan gelecek)
) {}