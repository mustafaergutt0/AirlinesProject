package com.ergutlarholding.searchservice.Repository;

import com.ergutlarholding.searchservice.Index.FlightIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightSearchRepository extends ElasticsearchRepository<FlightIndex, String> {
    // İleride buraya şehre veya fiyata göre arama metodları ekleyebiliriz
    void queryByArrivalAirportName(String arrivalAirportName);

}