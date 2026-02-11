package com.ergutlarholding.pilotservice.Mapper;

import com.ergutlarholding.pilotservice.Dto.PilotRequest;
import com.ergutlarholding.pilotservice.Dto.PilotResponse;
import com.ergutlarholding.pilotservice.Entity.Pilot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PilotMapper {
    Pilot toEntity(PilotRequest request);
    PilotResponse toResponse(Pilot pilot);
    List<PilotResponse> toResponseList(List<Pilot> pilots);
    void updatePilotFromRequest(PilotRequest request, @MappingTarget Pilot pilot);
}