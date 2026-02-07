package com.ergutlarholding.airlinesproject.Servis;

import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotLogin;
import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotRequest;
import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotResponse;
import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotSalaryUptade;
import com.ergutlarholding.airlinesproject.Entity.Pilot;
import com.ergutlarholding.airlinesproject.Repository.PilotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PilotService {

    private final PilotRepository pilotRepository;

    // Pilot Kaydı (Başlangıç maaşı ve saati burada atanır)
    public PilotResponse savePilot(PilotRequest request) {
        Pilot pilot = Pilot.builder()
                .name(request.name())
                .surname(request.surname())
                .mail(request.mail())
                .password(request.password())
                .licenseNumber(request.licenseNumber())
                .birthDate(request.birthDate())
                .flightHours(0)
                .salary(BigDecimal.valueOf(25000))
                .build();

        return mapToResponse(pilotRepository.save(pilot));
    }

    // Tüm Pilotları Listele
    public List<PilotResponse> getAllPilots() {
        return pilotRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ID ile Getir
    public PilotResponse getPilotById(Long id) {
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot bulunamadı!"));
        return mapToResponse(pilot);
    }

    // Genel Bilgileri Güncelle (Maaş hariç)
    public PilotResponse updatePilot(Long id, PilotRequest request) {
        Pilot existingPilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek pilot bulunamadı!"));

        existingPilot.setName(request.name());
        existingPilot.setSurname(request.surname());
        existingPilot.setMail(request.mail());
        existingPilot.setLicenseNumber(request.licenseNumber());
        existingPilot.setBirthDate(request.birthDate());

        return mapToResponse(pilotRepository.save(existingPilot));
    }

    // ÖZEL: Sadece Maaş Güncelleme (Ayrı DTO ile)
    public PilotResponse updateSalary(Long id, PilotSalaryUptade salaryUpdate) {
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maaşı güncellenecek pilot bulunamadı!"));

        pilot.setSalary(salaryUpdate.newSalary());
        return mapToResponse(pilotRepository.save(pilot));
    }

    // Pilot Sil
    public void deletePilot(Long id) {
        if (!pilotRepository.existsById(id)) {
            throw new RuntimeException("Silinecek pilot sistemde yok!");
        }
        pilotRepository.deleteById(id);
    }

    // Helper: Entity to DTO
    private PilotResponse mapToResponse(Pilot pilot) {
        return new PilotResponse(
                pilot.getId(),
                pilot.getName(),
                pilot.getSurname(),
                pilot.getMail(),
                pilot.getLicenseNumber(),
                pilot.getFlightHours(),
                pilot.getSalary(),
                pilot.getBirthDate()
        );
    }


    // PilotService.java içine ekle
    public PilotResponse login(PilotLogin loginRequest) {
        // 1. Mail adresiyle pilotu bul
        Pilot pilot = pilotRepository.findByMail(loginRequest.mail())
                .orElseThrow(() -> new RuntimeException("Bu mail adresiyle kayıtlı pilot bulunamadı!"));

        // 2. Şifreyi kontrol et (Gerçek projede BCrypt ile encode edilir ama şimdilik düz metin)
        if (!pilot.getPassword().equals(loginRequest.password())) {
            throw new RuntimeException("Hatalı şifre!");
        }

        // 3. Giriş başarılıysa pilot bilgilerini dön
        return mapToResponse(pilot);
    }
}