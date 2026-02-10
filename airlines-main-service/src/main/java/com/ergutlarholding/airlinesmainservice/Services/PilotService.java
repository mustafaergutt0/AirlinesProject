package com.ergutlarholding.airlinesmainservice.Services;

import com.ergutlarholding.airlinesmainservice.Dto.Pilot.PilotLogin;
import com.ergutlarholding.airlinesmainservice.Dto.Pilot.PilotRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Pilot.PilotResponse;
import com.ergutlarholding.airlinesmainservice.Dto.Pilot.PilotSalaryUptade;
import com.ergutlarholding.airlinesmainservice.Entity.Pilot;
import com.ergutlarholding.airlinesmainservice.Mapper.PilotMapper;
import com.ergutlarholding.airlinesmainservice.Repository.PilotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PilotService {

    private final PilotRepository pilotRepository;
    private final PilotMapper pilotMapper; // MapStruct yardımcımız

    // Pilot Kaydı (İş kuralları burada korunuyor)
    public PilotResponse savePilot(PilotRequest request) {
        Pilot pilot = pilotMapper.toEntity(request);

        // Başlangıç değerleri (İş mantığı)
        pilot.setFlightHours(0);
        pilot.setSalary(BigDecimal.valueOf(25000));

        return pilotMapper.toResponse(pilotRepository.save(pilot));
    }

    public List<PilotResponse> getAllPilots() {
        return pilotMapper.toResponseList(pilotRepository.findAll());
    }

    public PilotResponse getPilotById(Long id) {
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot bulunamadı!"));
        return pilotMapper.toResponse(pilot);
    }

    @Transactional
    public PilotResponse update(Long id, PilotRequest request) {
        Pilot existingPilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek pilot bulunamadı!"));

        // MapStruct ile genel bilgiler güncellendi (Maaş ve Saat korundu)
        pilotMapper.updatePilotFromRequest(request, existingPilot);

        return pilotMapper.toResponse(pilotRepository.save(existingPilot));
    }

    @Transactional
    public PilotResponse updateSalary(Long id, PilotSalaryUptade salaryUpdate) {
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maaşı güncellenecek pilot bulunamadı!"));

        pilot.setSalary(salaryUpdate.newSalary());
        return pilotMapper.toResponse(pilotRepository.save(pilot));
    }

    public void deletePilot(Long id) {
        if (!pilotRepository.existsById(id)) {
            throw new RuntimeException("Silinecek pilot sistemde yok!");
        }
        pilotRepository.deleteById(id);
    }

    public PilotResponse login(PilotLogin loginRequest) {
        Pilot pilot = pilotRepository.findByMail(loginRequest.mail())
                .orElseThrow(() -> new RuntimeException("Bu mail adresiyle kayıtlı pilot bulunamadı!"));

        if (!pilot.getPassword().equals(loginRequest.password())) {
            throw new RuntimeException("Hatalı şifre!");
        }

        return pilotMapper.toResponse(pilot);
    }
}