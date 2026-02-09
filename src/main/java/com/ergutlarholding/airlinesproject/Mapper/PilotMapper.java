package com.ergutlarholding.airlinesproject.Mapper;

import com.ergutlarholding.airlinesproject.Dto.Pilot.*;
import com.ergutlarholding.airlinesproject.Entity.Pilot;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PilotMapper {

    // Entity -> Response
    PilotResponse toResponse(Pilot pilot);

    // Request -> Entity (Maaş ve Uçuş Saati Service'te atanacak)
    Pilot toEntity(PilotRequest request);

    // Liste dönüşümü
    List<PilotResponse> toResponseList(List<Pilot> pilots);

    // Güncelleme: Mevcut pilotu Request ile ezer (Maaş ve Uçuş Saati değişmez)
    void updatePilotFromRequest(PilotRequest request, @MappingTarget Pilot pilot);
}