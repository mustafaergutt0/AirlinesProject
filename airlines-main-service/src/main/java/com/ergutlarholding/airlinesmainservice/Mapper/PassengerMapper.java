package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Passenger;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PassengerMapper {

    // Entity -> Response
    PassengerResponse toResponse(Passenger passenger);

    // Request -> Entity
    Passenger toEntity(PassengerRequest request);

    // Liste dönüşümü
    List<PassengerResponse> toResponseList(List<Passenger> passengers);

    // Mevcut yolcuyu Request ile güncelleme
    void updatePassengerFromRequest(PassengerRequest request, @MappingTarget Passenger passenger);
}