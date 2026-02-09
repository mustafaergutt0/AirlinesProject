package com.ergutlarholding.airlinesproject.Dto.Ticket;

import lombok.Builder;

@Builder
public record TicketRequest(
        String seatNumber,
        String ticketClass, // Economy, Business vb.
        Long flightId,      // Hangi uçuş?
        Long passengerId    // Kim alıyor?
) {}