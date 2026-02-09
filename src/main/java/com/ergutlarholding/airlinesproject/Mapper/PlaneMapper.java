package com.ergutlarholding.airlinesproject.Mapper;

import com.ergutlarholding.airlinesproject.Dto.Plane.PlaneResponse;
import com.ergutlarholding.airlinesproject.Entity.Plane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaneMapper {

    // Entity -> Response
    // request'teki kuralına göre havalimanının adını veya ülkesini çekiyoruz
    @Mapping(source = "currentAirport.name", target = "currentAirportName", defaultValue = "Yolda")
    PlaneResponse toResponse(Plane plane);

    // Liste dönüşümü
    List<PlaneResponse> toResponseList(List<Plane> planes);
}