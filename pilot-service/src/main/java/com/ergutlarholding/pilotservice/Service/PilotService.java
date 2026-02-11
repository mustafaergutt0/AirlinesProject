package com.ergutlarholding.pilotservice.Service;

import com.ergutlarholding.pilotservice.Dto.PilotRequest;
import com.ergutlarholding.pilotservice.Dto.PilotResponse;
import com.ergutlarholding.pilotservice.Dto.PilotSalaryUptade;
import com.ergutlarholding.pilotservice.Entity.Pilot;
import com.ergutlarholding.pilotservice.Mapper.PilotMapper;
import com.ergutlarholding.pilotservice.Repository.PilotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PilotService {
    private final PilotRepository pilotRepository;
    private final PilotMapper pilotMapper;

    public PilotResponse savePilot(PilotRequest request) {
        Pilot pilot = pilotMapper.toEntity(request);
        pilot.setSalary(BigDecimal.valueOf(25000));
        return pilotMapper.toResponse(pilotRepository.save(pilot));
    }

    public List<PilotResponse> getAllPilots() {
        return pilotMapper.toResponseList(pilotRepository.findAll());
    }

    public PilotResponse getPilotById(Long id) {
        return pilotMapper.toResponse(pilotRepository.findById(id).orElseThrow(() -> new RuntimeException("Pilot Bulunamadı")));
    }

    @Transactional
    public PilotResponse updateSalary(Long id, PilotSalaryUptade pilotSalaryUptade) {
        Pilot pilot = pilotRepository.findById(id).orElseThrow(() -> new RuntimeException("Pilot Bulunamadı"));
        pilot.setSalary(pilotSalaryUptade.newSalary());
        return pilotMapper.toResponse(pilotRepository.save(pilot));
    }

    // Diğer metodları (update, delete, login) buraya ekleyebilirsin.
}