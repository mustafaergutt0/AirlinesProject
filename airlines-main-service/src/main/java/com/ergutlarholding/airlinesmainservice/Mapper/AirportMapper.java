package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Airport.AirportRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Airport.AirportResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper extends BaseMapper<Airport, AirportRequest, AirportResponse> {
    // İçerisi tamamen boş!
    // BaseMapper sayesinde toResponse, toEntity, toResponseList ve updateEntityFromDto otomatik geldi.
}