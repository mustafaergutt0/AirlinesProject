package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    // Artık source = "pilot.name" diyemeyiz çünkü pilot nesnesi silindi!
    @Mapping(source = "pilotId", target = "pilotId")
    @Mapping(source = "plane.planeName", target = "planeName")
    @Mapping(source = "departureAirport.name", target = "departureAirportName")
    @Mapping(source = "arrivalAirport.name", target = "arrivalAirportName")
    FlightResponse toResponse(Flight flight);

    List<FlightResponse> toResponseList(List<Flight> flights);
}