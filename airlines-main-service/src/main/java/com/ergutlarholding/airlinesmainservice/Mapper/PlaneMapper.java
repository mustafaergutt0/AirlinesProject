package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Plane.PlaneRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Plane.PlaneResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Plane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaneMapper extends BaseMapper<Plane, PlaneRequest, PlaneResponse> {

    @Override
    @Mapping(source = "currentAirport.name", target = "currentAirportName", defaultValue = "Yolda")
    PlaneResponse toResponse(Plane plane);
}