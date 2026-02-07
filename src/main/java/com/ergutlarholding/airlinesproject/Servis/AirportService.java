package com.ergutlarholding.airlinesproject.Servis;

import com.ergutlarholding.airlinesproject.Dto.Airport.AirportRequest;
import com.ergutlarholding.airlinesproject.Dto.Airport.AirportResponse;
import com.ergutlarholding.airlinesproject.Entity.Airport;
import com.ergutlarholding.airlinesproject.Repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportResponse saveAirport(AirportRequest request) {
        Airport airport = Airport.builder()
                .name(request.name())
                .city(request.city())
                .country(request.country())
                .code(request.code().toUpperCase()) // Kodları her zaman büyük harf yapalım
                .build();

        return mapToResponse(airportRepository.save(airport));
    }

    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AirportResponse mapToResponse(Airport airport) {
        return new AirportResponse(
                airport.getId(),
                airport.getName(),
                airport.getCity(),
                airport.getCountry(),
                airport.getCode()
        );
    }

    public String deleteAirportByCode(String code) {
        Airport airport = airportRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Silinecek havalimanı bulunamadı: " + code));

        airportRepository.deleteByCode(airport.getCode());
        return code + " kodlu havalimanı başarıyla silindi.";
    }

    public AirportResponse showAirport(String code) {
        return airportRepository.findByCode(code.toUpperCase())
                .map(this::mapToResponse) // Eğer havalimanı varsa Response'a çevir
                .orElseThrow(() -> new RuntimeException("Havalimanı bulunamadı: " + code)); // Yoksa hata fırlat
    }

    public AirportResponse updateAirport(String code, AirportRequest airportRequest) {
        // 1. Önce güncellenecek havalimanını veritabanında bul
        Airport existingAirport = airportRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Güncellenecek havalimanı bulunamadı: " + code));

        // 2. Mevcut nesnenin bilgilerini request'ten gelenlerle güncelle
        existingAirport.setName(airportRequest.name());
        existingAirport.setCity(airportRequest.city());
        existingAirport.setCountry(airportRequest.country());
        existingAirport.setCode(airportRequest.code().toUpperCase());

        // 3. Güncellenmiş nesneyi kaydet ve response olarak dön
        Airport updated = airportRepository.save(existingAirport);
        return mapToResponse(updated);
    }


    public AirportResponse patchAirport(String code, Map<String, Object> updates) {
        // 1. Önce havalimanını bul
        Airport airport = airportRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Güncellenecek havalimanı bulunamadı: " + code));

        // 2. Map içindeki anahtarlara göre sadece ilgili alanları güncelle
        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> airport.setName((String) value);
                case "city" -> airport.setCity((String) value);
                case "country" -> airport.setCountry((String) value);
                case "code" -> airport.setCode(((String) value).toUpperCase());
            }  // hangi alan bulunursa onu güncelleyecek swicth key yapıs onu sağlıyro
        });

        // 3. Değişiklikleri kaydet
        return mapToResponse(airportRepository.save(airport));
    }


}