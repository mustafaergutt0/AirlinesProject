package com.ergutlarholding.airlinesmainservice.Services;

import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Airport;
import com.ergutlarholding.airlinesmainservice.Entity.Flight;
import com.ergutlarholding.airlinesmainservice.Entity.Plane;
import com.ergutlarholding.airlinesmainservice.Mapper.FlightMapper;
import com.ergutlarholding.airlinesmainservice.Repository.AirportRepository;
import com.ergutlarholding.airlinesmainservice.Repository.FlightRepository;
import com.ergutlarholding.airlinesmainservice.Repository.PlaneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    @Transactional
    public FlightResponse createFlight(FlightRequest request) {
        // 1. Mevcut Servis İçindeki Doğrulamalar (Uçak ve Havalimanı bu serviste)
        Plane plane = planeRepository.findById(request.planeId())
                .orElseThrow(() -> new RuntimeException("Uçak bulunamadı!"));

        Airport depAirport = airportRepository.findById(request.departureAirportId())
                .orElseThrow(() -> new RuntimeException("Kalkış havalimanı bulunamadı!"));

        Airport arrAirport = airportRepository.findById(request.arrivalAirportId())
                .orElseThrow(() -> new RuntimeException("Varış havalimanı bulunamadı!"));

        // NOT: Pilot doğrulaması (pilotRepository.findById) buradan kalktı!
        // Çünkü Pilot artık başka bir servisin (pilot-service) veritabanında.

        // 2. Entity Oluşturma (ID bazlı ilişki)
        Flight flight = Flight.builder()
                .flightCode(request.flightCode().toUpperCase())
                .price(request.price())
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .pilotId(request.pilotId()) // Sadece ID'yi kaydediyoruz
                .plane(plane)
                .departureAirport(depAirport)
                .arrivalAirport(arrAirport)
                .build();

        // 3. Kaydet ve Dönüştür
        Flight savedFlight = flightRepository.save(flight);
        return flightMapper.toResponse(savedFlight);
    }

    public List<FlightResponse> getAllFlights() {
        return flightMapper.toResponseList(flightRepository.findAll());
    }

    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Silinecek uçuş bulunamadı!");
        }
        flightRepository.deleteById(id);
    }
}