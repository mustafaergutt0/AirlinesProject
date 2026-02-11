package com.ergutlarholding.pilotservice.Controller;

import com.ergutlarholding.pilotservice.Dto.*;
import com.ergutlarholding.pilotservice.Service.PilotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pilots")
@RequiredArgsConstructor
public class PilotController {
    private final PilotService pilotService;

    @PostMapping("/save")
    public ResponseEntity<PilotResponse> register(@RequestBody PilotRequest request) {
        return ResponseEntity.ok(pilotService.savePilot(request));
    }

    @GetMapping
    public ResponseEntity<List<PilotResponse>> getAll() {
        return ResponseEntity.ok(pilotService.getAllPilots());
    }

    @PatchMapping("/{id}/salary")
    public ResponseEntity<PilotResponse> updateSalary(@PathVariable Long id, @RequestBody PilotSalaryUptade uptade) {
        return ResponseEntity.ok(pilotService.updateSalary(id, uptade));
    }
}