package com.ergutlarholding.searchservice.Dto;

import com.ergutlarholding.searchservice.Index.FlightIndex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchResponseDTO {
    // Listelenecek uçuşlar (Orta panel)
    private List<FlightIndex> flights;

    // Dinamik filtreler (Sol/Sağ panel kutucukları)
    // Örn: "Istanbul" -> 5, "Berlin" -> 2
    private Map<String, Long> departureCityFilters;
    private Map<String, Long> arrivalCityFilters;

    private long totalFound;
}