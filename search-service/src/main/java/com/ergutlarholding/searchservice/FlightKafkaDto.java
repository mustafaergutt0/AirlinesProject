package com.ergutlarholding.searchservice; // Search tarafında paket ismine dikkat!

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightKafkaDto(
        Long id,                       // Ana veritabanındaki ID (Güncelleme işlemleri için)
        String flightCode,             // Uçuş kodu (TK1903 vb.)
        BigDecimal price,              // Fiyat
        String departureAirportName,   // "İstanbul Havalimanı" (ID yerine isim)
        String arrivalAirportName,     // "Esenboğa Havalimanı" (ID yerine isim)
        String departureCity,          // "İstanbul"
        String arrivalCity,            // "Ankara"
        LocalDateTime departureTime,   // Kalkış zamanı
        LocalDateTime arrivalTime      // Varış zamanı
) {}