package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Ticket.TicketResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    // Entity -> Response DTO
    @Mapping(source = "passenger.name", target = "passengerName")
    @Mapping(source = "flight.flightCode", target = "flightCode")
    @Mapping(source = "flight.departureAirport.name", target = "departureAirport")
    @Mapping(source = "flight.arrivalAirport.name", target = "arrivalAirport")
    @Mapping(source = "flight.price", target = "price")
    TicketResponse toResponse(Ticket ticket);

    // Liste dönüşümü
    List<TicketResponse> toResponseList(List<Ticket> tickets);
}