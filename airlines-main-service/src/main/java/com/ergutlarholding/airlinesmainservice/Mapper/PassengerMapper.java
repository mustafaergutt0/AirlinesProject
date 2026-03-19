package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Passenger;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper extends BaseMapper<Passenger, PassengerRequest, PassengerResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authId", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Passenger toEntity(PassengerRequest request);

    @Override
    @Mapping(target = "mail", ignore = true) // Response içinde mail bekliyorsa ama entity'de yoksa ignore et
    PassengerResponse toResponse(Passenger passenger);
}