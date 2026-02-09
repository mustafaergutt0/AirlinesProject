package com.ergutlarholding.airlinesproject.Servis;

import com.ergutlarholding.airlinesproject.Dto.Flight.*;
import com.ergutlarholding.airlinesproject.Entity.*;
import com.ergutlarholding.airlinesproject.Repository.*;
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

    public FlightResponse createFlight(FlightRequest request) {
        // Nesneleri tek tek bulalım (Bağlantıların doğrulanması)
        Pilot pilot = pilotRepository.findById(request.pilotId())
                .orElseThrow(() -> new RuntimeException("Pilot bulunamadı!"));
        Plane plane = planeRepository.findById(request.planeId())
                .orElseThrow(() -> new RuntimeException("Uçak bulunamadı!"));
        Airport depAirport = airportRepository.findById(request.departureAirportId())
                .orElseThrow(() -> new RuntimeException("Kalkış havalimanı bulunamadı!"));
        Airport arrAirport = airportRepository.findById(request.arrivalAirportId())
                .orElseThrow(() -> new RuntimeException("Varış havalimanı bulunamadı!"));

        Flight flight = Flight.builder()
                .flightCode(request.flightCode())
                .price(request.price())
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .pilot(pilot)
                .plane(plane)
                .departureAirport(depAirport)
                .arrivalAirport(arrAirport)
                .build();

        return mapToResponse(flightRepository.save(flight));
    }

    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    private FlightResponse mapToResponse(Flight flight) {
        return new FlightResponse(
                flight.getId(),
                flight.getFlightCode(),
                flight.getPrice(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getPilot().getName(),
                flight.getPlane().getPlaneName(),
                flight.getDepartureAirport().getName(),
                flight.getArrivalAirport().getName()
        );
    }
}