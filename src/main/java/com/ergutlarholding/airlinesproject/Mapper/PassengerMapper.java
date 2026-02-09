package com.ergutlarholding.airlinesproject.Mapper;

import com.ergutlarholding.airlinesproject.Dto.Passenger.*;
import com.ergutlarholding.airlinesproject.Entity.Passenger;
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