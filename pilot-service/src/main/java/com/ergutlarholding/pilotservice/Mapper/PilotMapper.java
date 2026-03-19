package com.ergutlarholding.pilotservice.Mapper;

import com.ergutlarholding.pilotservice.Dto.PilotRequest;
import com.ergutlarholding.pilotservice.Dto.PilotResponse;
import com.ergutlarholding.pilotservice.Entity.Pilot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PilotMapper {

    // Request'ten Entity'ye çevirirken id, authId ve diğer otomatik alanları MapStruct'ın doldurmasını engelliyoruz
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "salary", ignore = true)
    @Mapping(target = "flightHours", ignore = true)
    Pilot toEntity(PilotRequest request);

    // Entity'den Response'a çevrim
    PilotResponse toResponse(Pilot pilot);

    // Liste çevrimi
    List<PilotResponse> toResponseList(List<Pilot> pilots);

    // Güncelleme işlemi için (gerektiğinde)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authId", ignore = true)
    void updatePilotFromRequest(PilotRequest request, @MappingTarget Pilot pilot);
}