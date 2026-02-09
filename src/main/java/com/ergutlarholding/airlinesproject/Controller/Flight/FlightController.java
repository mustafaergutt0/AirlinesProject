package com.ergutlarholding.airlinesproject.Controller.Flight;

import com.ergutlarholding.airlinesproject.Dto.Flight.*;
import com.ergutlarholding.airlinesproject.Servis.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/create")
    public ResponseEntity<FlightResponse> create(@RequestBody FlightRequest request) {
        return ResponseEntity.ok(flightService.createFlight(request));
    }

    @GetMapping("/getAllFlights")
    public ResponseEntity<List<FlightResponse>> getAll() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Uçuş iptal edildi.");
    }
}