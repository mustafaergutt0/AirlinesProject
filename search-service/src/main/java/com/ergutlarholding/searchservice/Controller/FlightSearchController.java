package com.ergutlarholding.searchservice.Controller;


import com.ergutlarholding.searchservice.Dto.FlightSearchResponseDTO;
import com.ergutlarholding.searchservice.Index.FlightIndex;
import com.ergutlarholding.searchservice.Service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @GetMapping("/flights")
    public ResponseEntity<FlightSearchResponseDTO> searchFlights(@RequestParam String query) {
        return ResponseEntity.ok(flightSearchService.searchFlights(query));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightIndex>> getAll() {
        return ResponseEntity.ok(flightSearchService.getAllFlights());
    }
}
