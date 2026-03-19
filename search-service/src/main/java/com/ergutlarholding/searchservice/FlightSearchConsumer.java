package com.ergutlarholding.searchservice;

import com.ergutlarholding.searchservice.FlightKafkaDto;
import com.ergutlarholding.searchservice.Index.FlightIndex;
import com.ergutlarholding.searchservice.Repository.FlightSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor // Repository'nin enjekte edilmesini sağlar
public class FlightSearchConsumer {

    private final FlightSearchRepository searchRepository;

    @KafkaListener(topics = "FlightElasticSearch", groupId = "search-group-final-v1",
            properties = {"spring.json.value.default.type=com.ergutlarholding.searchservice.FlightKafkaDto", "spring.json.use.type.headers=false"})
    public void consumeFlight(FlightKafkaDto message) {
        log.info("Kafka'dan mesaj geldi: {}", message.flightCode());

        try {
            // DTO'dan INDEX'e dönüşüm
            FlightIndex index = FlightIndex.builder()
                    .id(message.id().toString())
                    .flightCode(message.flightCode())
                    .price(message.price().doubleValue())
                    .departureAirportName(message.departureAirportName())
                    .arrivalAirportName(message.arrivalAirportName())
                    .departureCity(message.departureCity())
                    .arrivalCity(message.arrivalCity())
                    .departureTime(message.departureTime())
                    .arrivalTime(message.arrivalTime())
                    .build();

            searchRepository.save(index);
            log.info("Elasticsearch kaydı tamamlandı: {}", index.getFlightCode());
        } catch (Exception e) {
            log.error("Hata oluştu: {}", e.getMessage());
        }
    }
}