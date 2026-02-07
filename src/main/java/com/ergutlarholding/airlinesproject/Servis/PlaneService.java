package com.ergutlarholding.airlinesproject.Servis;

import com.ergutlarholding.airlinesproject.Dto.Plane.PlaneRequest;
import com.ergutlarholding.airlinesproject.Dto.Plane.PlaneResponse;
import com.ergutlarholding.airlinesproject.Entity.Airport;
import com.ergutlarholding.airlinesproject.Entity.Plane;
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

    public PlaneResponse savePlane(PlaneRequest request) {
        // Havalimanını bul
        Airport airport = airportRepository.findById(request.currentAirportId())
                .orElseThrow(() -> new RuntimeException("Havalimanı bulunamadı!"));

        Plane plane = Plane.builder()
                .planeName(request.planeName())
                .model(request.model())
                .totalSeatCount(request.totalSeatCount())
                .currentAirport(airport)
                .build();

        return mapToResponse(planeRepository.save(plane));
    }

    public List<PlaneResponse> getAllPlanes() {
        return planeRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PlaneResponse mapToResponse(Plane plane) {
        return new PlaneResponse(
                plane.getPlaneId(),
                plane.getPlaneName(),
                plane.getModel(),
                plane.getTotalSeatCount(),
                plane.getCurrentAirport() != null ? plane.getCurrentAirport().getCountry() : "Yolda"
        );
    }

    public String DeleteAllPlanes() {
        planeRepository.deleteAll();
        return "Bütün Filo boşaltıldı";
    }
}