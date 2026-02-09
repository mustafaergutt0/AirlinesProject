package com.ergutlarholding.airlinesproject.Services;

import com.ergutlarholding.airlinesproject.Dto.Airport.AirportRequest;
import com.ergutlarholding.airlinesproject.Dto.Airport.AirportResponse;
import com.ergutlarholding.airlinesproject.Entity.Airport;
import com.ergutlarholding.airlinesproject.Mapper.AirportMapper;
import com.ergutlarholding.airlinesproject.Repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper; // Mapper enjekte edildi

    public AirportResponse saveAirport(AirportRequest request) {
        // Builder yerine MapStruct kullanarak Entity oluşturduk
        Airport airport = airportMapper.toEntity(request);
        airport.setCode(request.code().toUpperCase()); // Senin kuralın burada duruyor

        return airportMapper.toResponse(airportRepository.save(airport));
    }

    public List<AirportResponse> getAllAirports() {
        // Stream ve map yerine tek satırda liste dönüşümü
        return airportMapper.toResponseList(airportRepository.findAll());
    }

    public String deleteAirportByCode(String code) {
        Airport airport = airportRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Silinecek havalimanı bulunamadı: " + code));

        airportRepository.deleteByCode(airport.getCode());
        return code + " kodlu havalimanı başarıyla silindi.";
    }

    public AirportResponse showAirport(String code) {
        return airportRepository.findByCode(code.toUpperCase())
                .map(airportMapper::toResponse) // Manuel mapToResponse yerine mapper kullanıldı
                .orElseThrow(() -> new RuntimeException("Havalimanı bulunamadı: " + code));
    }

    public AirportResponse updateAirport(String code, AirportRequest airportRequest) {
        Airport existingAirport = airportRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Güncellenecek havalimanı bulunamadı: " + code));

        // Manuel set'ler yerine MapStruct ile tüm alanları güncelledik
        airportMapper.updateEntityFromDto(airportRequest, existingAirport);
        existingAirport.setCode(airportRequest.code().toUpperCase()); // Kuralı koruduk

        return airportMapper.toResponse(airportRepository.save(existingAirport));
    }

    public AirportResponse patchAirport(String code, Map<String, Object> updates) {
        Airport airport = airportRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Güncellenecek havalimanı bulunamadı: " + code));

        // Senin özel switch-case yapını ellemedim, iş mantığın burada çalışıyor
        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> airport.setName((String) value);
                case "city" -> airport.setCity((String) value);
                case "country" -> airport.setCountry((String) value);
                case "code" -> airport.setCode(((String) value).toUpperCase());
            }
        });

        return airportMapper.toResponse(airportRepository.save(airport));
    }
}