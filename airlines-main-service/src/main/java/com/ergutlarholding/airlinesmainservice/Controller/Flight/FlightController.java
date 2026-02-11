package com.ergutlarholding.airlinesmainservice.Controller.Flight;

import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Flight.FlightResponse;
import com.ergutlarholding.airlinesmainservice.Services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/main/flights")
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