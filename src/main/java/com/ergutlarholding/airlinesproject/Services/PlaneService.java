package com.ergutlarholding.airlinesproject.Services;

import com.ergutlarholding.airlinesproject.Dto.Plane.PlaneRequest;
import com.ergutlarholding.airlinesproject.Dto.Plane.PlaneResponse;
import com.ergutlarholding.airlinesproject.Entity.Airport;
import com.ergutlarholding.airlinesproject.Entity.Plane;
import com.ergutlarholding.airlinesproject.Mapper.PlaneMapper;
import com.ergutlarholding.airlinesproject.Repository.AirportRepository;
import com.ergutlarholding.airlinesproject.Repository.PlaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaneService {

    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepository;
    private final PlaneMapper planeMapper; // MapStruct enjekte edildi

    public PlaneResponse savePlane(PlaneRequest request) {
        // İş Mantığı: Havalimanını bul
        Airport airport = airportRepository.findById(request.currentAirportId())
                .orElseThrow(() -> new RuntimeException("Havalimanı bulunamadı!"));

        Plane plane = Plane.builder()
                .planeName(request.planeName())
                .model(request.model())
                .totalSeatCount(request.totalSeatCount())
                .currentAirport(airport)
                .build();

        return planeMapper.toResponse(planeRepository.save(plane));
    }

    public List<PlaneResponse> getAllPlanes() {
        return planeMapper.toResponseList(planeRepository.findAll());
    }

    public String deleteAllPlanes() {
        planeRepository.deleteAll();
        return "Bütün Filo başarıyla boşaltıldı.";
    }
}