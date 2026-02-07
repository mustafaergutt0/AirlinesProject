package com.ergutlarholding.airlinesproject.Controller.Passenger;
// Patch gelen isteiğin bir kısmını put gibi değil bır kısmı güncellecek demek


/*

1. PUT ve PATCH Arasındaki Fark Nedir?
Bir pilotun profilini bir dosya gibi düşün:

PUT (Tam Güncelleme): Mevcut dosyayı çöpe atar ve yerine senin gönderdiğin yeni ve tam dosyayı koyar. Eğer sen sadece ismi gönderip soyismi göndermezsen, veritabanındaki soyisim silinebilir veya boş kalabilir. (Tüm alanları göndermek zorundasın).

PATCH (Kısmi Güncelleme): Dosyayı açar, sadece senin belirlediğin alanı (örneğin sadece maaş) değiştirir, diğer her şeye dokunmadan dosyayı kapatır.




 */
import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotLogin;
import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotRequest;
import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotResponse;
import com.ergutlarholding.airlinesproject.Servis.PilotService;
import com.ergutlarholding.airlinesproject.Dto.Pilot.PilotSalaryUptade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pilots")
@RequiredArgsConstructor
public class PilotController {

    private final PilotService pilotService;

    @PostMapping("/register")
    public ResponseEntity<PilotResponse> register(@RequestBody PilotRequest request) {
        return ResponseEntity.ok(pilotService.savePilot(request));
    }

    @PostMapping("/login")
    public ResponseEntity<PilotResponse> login(@RequestBody PilotLogin pilotLogin) {
        return ResponseEntity.ok(pilotService.login(pilotLogin));

    }

    @GetMapping
    public ResponseEntity<List<PilotResponse>> getAll() {
        return ResponseEntity.ok(pilotService.getAllPilots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PilotResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pilotService.getPilotById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PilotResponse> update(@PathVariable Long id, @RequestBody PilotRequest request) {
        return ResponseEntity.ok(pilotService.updatePilot(id, request));
    }

    @PatchMapping("/{id}/salary")
    public ResponseEntity<PilotResponse> updateSalary(
            @PathVariable Long id,
            @RequestBody PilotSalaryUptade salaryUpdate) {
        return ResponseEntity.ok(pilotService.updateSalary(id, salaryUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pilotService.deletePilot(id);
        return ResponseEntity.noContent().build();
    }
}