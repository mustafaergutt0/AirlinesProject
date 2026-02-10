package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    // Entity -> Response DTO
    @Mapping(source = "pilot.name", target = "pilotName")
    @Mapping(source = "plane.planeName", target = "planeName")
    @Mapping(source = "departureAirport.name", target = "departureAirportName")
    @Mapping(source = "arrivalAirport.name", target = "arrivalAirportName")
    FlightResponse toResponse(Flight flight);

    // Liste dönüşümü
    List<FlightResponse> toResponseList(List<Flight> flights);
}