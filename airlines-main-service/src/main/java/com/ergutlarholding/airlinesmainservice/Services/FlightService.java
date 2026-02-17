package com.ergutlarholding.airlinesmainservice.Services;

import com.ergutlarholding.airlinesmainservice.Client.Dto.PilotResponse;
import com.ergutlarholding.airlinesmainservice.Client.Pilot_Client;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightLogDto;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightResponse;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightLogDto; // Senin oluşturduğun DTO
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
import org.springframework.amqp.rabbit.core.RabbitTemplate; // RabbitMQ için fırlatıcı
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;
    private final Pilot_Client pilotClient;

    // RabbitMQ mesajlarını göndermek için enjekte ettik
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public FlightResponse createFlight(FlightRequest request) {
        // 1. Yerel Veritabanı Kontrolleri (Uçak ve Havalimanları)
        Plane plane = planeRepository.findById(request.planeId())
                .orElseThrow(() -> new RuntimeException("Hata: Uçak bulunamadı!"));

        Airport depAirport = airportRepository.findById(request.departureAirportId())
                .orElseThrow(() -> new RuntimeException("Hata: Kalkış havalimanı bulunamadı!"));

        Airport arrAirport = airportRepository.findById(request.arrivalAirportId())
                .orElseThrow(() -> new RuntimeException("Hata: Varış havalimanı bulunamadı!"));

        // 2. Mikroservis Kontrolü (Senkron - Feign Client)
        log.info("Pilot doğrulanıyor... Pilot ID: {}", request.pilotId());
        try {
            PilotResponse pilot = pilotClient.getPilotById(request.pilotId());
            if (pilot == null) {
                throw new RuntimeException("Hata: Pilot bulunamadı!");
            }
        } catch (Exception e) {
            log.error("Pilot-Service ulaşılamaz durumda veya ID hatalı.");
            throw new RuntimeException("Uçuş oluşturulamadı: Pilot doğrulaması başarısız.");
        }

        // 3. Entity Oluşturma ve Kaydetme
        Flight flight = Flight.builder()
                .flightCode(request.flightCode().toUpperCase())
                .price(request.price())
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .pilotId(request.pilotId())
                .plane(plane)
                .departureAirport(depAirport)
                .arrivalAirport(arrAirport)
                .build();

        Flight savedFlight = flightRepository.save(flight);

        // 4. EDA (Event-Driven Architecture) - Log Servisine Haber Ver (Asenkron)
        try {
            FlightLogDto logEvent = new FlightLogDto(
                    "AIRLINES-MAIN-SERVICE",
                    "YENİ UÇUŞ KAYDI: " + savedFlight.getFlightCode() + " seferi oluşturuldu.",
                    LocalDateTime.now().toString()
            );

            // Mesajı RabbitMQ kuyruğuna (log_queue) gönderiyoruz
            rabbitTemplate.convertAndSend("FlightQueLog", logEvent);
            log.info("Log mesajı RabbitMQ kuyruğuna başarıyla iletildi.");
        } catch (Exception e) {
            // Log gitmedi diye ana işlemi (uçuş kaydını) bozmuyoruz.
            log.warn("Log mesajı gönderilemedi ama uçuş kaydedildi: {}", e.getMessage());
        }

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