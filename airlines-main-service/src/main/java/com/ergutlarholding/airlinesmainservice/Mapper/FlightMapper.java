package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper extends BaseMapper<Flight, FlightRequest, FlightResponse> {

    @Override // BaseMapper'daki metodu override ederek özel mapping kuralları ekliyoruz
    @Mapping(source = "plane.planeName", target = "planeName")
    @Mapping(source = "departureAirport.name", target = "departureAirportName")
    @Mapping(source = "arrivalAirport.name", target = "arrivalAirportName")
    FlightResponse toResponse(Flight flight);
}