package com.ergutlarholding.airlinesmainservice.Services;

import com.ergutlarholding.airlinesmainservice.Client.Dto.PilotResponse;
import com.ergutlarholding.airlinesmainservice.Client.Pilot_Client;
import com.ergutlarholding.airlinesmainservice.Client.Pilot_Client; // Yazdığın Feign Client
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j // Loglama için eklendi
public class FlightService {

    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    // ASIL PÜF NOKTASI: Mikroservisler arası iletişim için Feign Client enjeksiyonu
    private final Pilot_Client pilotClient;

    @Transactional
    public FlightResponse createFlight(FlightRequest request) {
        // 1. Yerel Veritabanı Kontrolleri (Uçak ve Havalimanları bu servisin db'sinde)
        Plane plane = planeRepository.findById(request.planeId())
                .orElseThrow(() -> new RuntimeException("Hata: Uçak bulunamadı!"));

        Airport depAirport = airportRepository.findById(request.departureAirportId())
                .orElseThrow(() -> new RuntimeException("Hata: Kalkış havalimanı bulunamadı!"));

        Airport arrAirport = airportRepository.findById(request.arrivalAirportId())
                .orElseThrow(() -> new RuntimeException("Hata: Varış havalimanı bulunamadı!"));

        // 2. Mikroservis Kontrolü: Pilot-Service'e "Telsiz" atıyoruz
        log.info("Pilot doğrulanıyor... Pilot ID: {}", request.pilotId());
        try {
            // Feign Client üzerinden diğer servise HTTP GET isteği gider
            PilotResponse pilot = pilotClient.getPilotById(request.pilotId());

            if (pilot == null) {
                throw new RuntimeException("Hata: Pilot-Service geçerli bir pilot dönmedi!");
            }
            log.info("Pilot doğrulandı: {} {}", pilot.name(), pilot.surname());

        } catch (Exception e) {
            // Pilot-Service kapalıysa veya 404/500 dönerse burası çalışır
            log.error("Pilot doğrulaması başarısız! Pilot-Service ulaşılamaz durumda veya ID hatalı.");
            throw new RuntimeException("Uçuş oluşturulamadı: Pilot doğrulaması başarısız oldu.");
        }

        // 3. Entity Oluşturma (Pilotun sadece ID'sini saklıyoruz)
        Flight flight = Flight.builder()
                .flightCode(request.flightCode().toUpperCase())
                .price(request.price())
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .pilotId(request.pilotId()) // Diğer servisteki ID'yi buraya kaydediyoruz
                .plane(plane)
                .departureAirport(depAirport)
                .arrivalAirport(arrAirport)
                .build();

        // 4. Kaydet ve Mapper ile Response'a dönüştür
        Flight savedFlight = flightRepository.save(flight);
        return flightMapper.toResponse(savedFlight);
    }

    public List<FlightResponse> getAllFlights() {
        return flightMapper.toResponseList(flightRepository.findAll());
    }

    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Hata: Silinecek uçuş bulunamadı!");
        }
        flightRepository.deleteById(id);
    }
}