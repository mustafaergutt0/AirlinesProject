package com.ergutlarholding.airlinesmainservice.Services;

import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Airport;
import com.ergutlarholding.airlinesmainservice.Entity.Flight;
import com.ergutlarholding.airlinesmainservice.Entity.Pilot;
import com.ergutlarholding.airlinesmainservice.Entity.Plane;
import com.ergutlarholding.airlinesmainservice.Mapper.FlightMapper;
import com.ergutlarholding.airlinesmainservice.Repository.AirportRepository;
import com.ergutlarholding.airlinesmainservice.Repository.FlightRepository;
import com.ergutlarholding.airlinesmainservice.Repository.PilotRepository;
import com.ergutlarholding.airlinesmainservice.Repository.PlaneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final PilotRepository pilotRepository;
    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper; // İşte yeni yardımcımız!

    @Transactional
    public FlightResponse createFlight(FlightRequest request) {
        // 1. Doğrulamalar (Aynen duruyor)
        Pilot pilot = pilotRepository.findById(request.pilotId())
                .orElseThrow(() -> new RuntimeException("Pilot bulunamadı!"));
        Plane plane = planeRepository.findById(request.planeId())
                .orElseThrow(() -> new RuntimeException("Uçak bulunamadı!"));
        Airport depAirport = airportRepository.findById(request.departureAirportId())
                .orElseThrow(() -> new RuntimeException("Kalkış havalimanı bulunamadı!"));
        Airport arrAirport = airportRepository.findById(request.arrivalAirportId())
                .orElseThrow(() -> new RuntimeException("Varış havalimanı bulunamadı!"));

        // 2. Entity Oluşturma (İş kuralları burada)
        Flight flight = Flight.builder()
                .flightCode(request.flightCode().toUpperCase()) // Küçük bir holding dokunuşu
                .price(request.price())
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .pilot(pilot)
                .plane(plane)
                .departureAirport(depAirport)
                .arrivalAirport(arrAirport)
                .build();

        // 3. Kaydet ve Mapper ile Dönüştür
        Flight savedFlight = flightRepository.save(flight);
        return flightMapper.toResponse(savedFlight); // Artık mapToResponse metodu yok!
    }

    public List<FlightResponse> getAllFlights() {
        // Stream().map() ameleliği bitti
        return flightMapper.toResponseList(flightRepository.findAll());
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);

    }
}