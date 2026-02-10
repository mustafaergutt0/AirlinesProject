package com.ergutlarholding.airlinesmainservice.Controller.Airport;
import com.ergutlarholding.airlinesmainservice.Services.AirportService;
import com.ergutlarholding.airlinesmainservice.Dto.Airport.AirportRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Airport.AirportResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping()
    public ResponseEntity<AirportResponse> save(@RequestBody AirportRequest request) {
        return ResponseEntity.ok(airportService.saveAirport(request));
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAll() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/{code}")
    public ResponseEntity<AirportResponse> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(airportService.showAirport(code));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable String code) {
        return ResponseEntity.ok(airportService.deleteAirportByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<AirportResponse> update(@PathVariable String code, @RequestBody AirportRequest request) {
        return ResponseEntity.ok(airportService.updateAirport(code, request));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<AirportResponse> patch(@PathVariable String code, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(airportService.patchAirport(code, updates));
        // bu methot sadece neyi değiştirisen onu değiştiriecek şekilde ayarlı olan bir methot


    }


}