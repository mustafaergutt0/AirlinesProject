package com.ergutlarholding.logservice.Service; // service klasöründe olmalı

import com.ergutlarholding.logservice.Dto.FlightLogDto;
import com.ergutlarholding.logservice.Entity.LogEntity;
import com.ergutlarholding.logservice.Repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogRead {

    // Artık kırmızı yanmaması gerekiyor
    @Autowired
    private final LogRepository     logRepository;

    @RabbitListener(queues = "FlightQueLog")
    public void consumeLogMessage(FlightLogDto logDto) {
        log.info("Kuyruktan yeni bir log mesajı alındı: {}", logDto.getMessage());

        try {
            LogEntity logEntity = new LogEntity();
            logEntity.setServiceName(logDto.getServiceName());
            logEntity.setMessage(logDto.getMessage());
            logEntity.setTimestamp(logDto.getTimestamp());

            logRepository.save(logEntity);

            log.info("Log başarıyla veritabanına kaydedildi.");
        } catch (Exception e) {
            log.error("Log kaydedilirken hata oluştu: {}", e.getMessage());
        }
    }
}