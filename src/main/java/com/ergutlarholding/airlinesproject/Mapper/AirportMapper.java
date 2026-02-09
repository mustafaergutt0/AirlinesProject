package com.ergutlarholding.airlinesproject.Mapper;

import com.ergutlarholding.airlinesproject.Dto.Airport.AirportRequest;
import com.ergutlarholding.airlinesproject.Dto.Airport.AirportResponse;
import com.ergutlarholding.airlinesproject.Entity.Airport;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    // Entity -> DTO
    AirportResponse toResponse(Airport airport);

    // DTO -> Entity
    Airport toEntity(AirportRequest request);

    // Liste Dönüşümü
    List<AirportResponse> toResponseList(List<Airport> airports);

    // Güncelleme/Patch: Mevcut entity'yi DTO verileriyle günceller
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AirportRequest request, @MappingTarget Airport airport);
}