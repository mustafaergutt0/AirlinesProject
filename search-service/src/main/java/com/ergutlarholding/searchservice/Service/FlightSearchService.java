package com.ergutlarholding.searchservice.Service;

import com.ergutlarholding.searchservice.Dto.FlightSearchResponseDTO;
import com.ergutlarholding.searchservice.Index.FlightIndex;

import com.ergutlarholding.searchservice.Repository.FlightSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

// BU IMPORTLARIN TAM OLDUĞUNDAN EMİN OL
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final FlightSearchRepository  flightSearchRepository;

    public FlightSearchResponseDTO searchFlights(String queryText) {

        // 1. Sorgu ve Aggregation Tanımları
        Query multiMatchQuery = Query.of(q -> q.multiMatch(m -> m.fields("departureCity", "arrivalCity").query(queryText)));

        Aggregation depAggregation = Aggregation.of(a -> a.terms(t -> t.field("departureCity")));
        Aggregation arrAggregation = Aggregation.of(a -> a.terms(t -> t.field("arrivalCity")));

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(multiMatchQuery)
                .withAggregation("departure_counts", depAggregation)
                .withAggregation("arrival_counts", arrAggregation)
                .build();

        // 2. Arama
        SearchHits<FlightIndex> searchHits = elasticsearchOperations.search(searchQuery, FlightIndex.class);

        // 3. Uçuşları Ayıkla
        List<FlightIndex> flights = searchHits.stream().map(SearchHit::getContent).toList();

        // 4. Filtreleri Ayıkla (Hatalı Bölümün Düzeltilmiş Hali)
        Map<String, Long> depFilters = new HashMap<>();
        Map<String, Long> arrFilters = new HashMap<>();

        if (searchHits.getAggregations() instanceof ElasticsearchAggregations esAggs) {

            // Departure Filtreleme
            var depResult = esAggs.get("departure_counts");
            if (depResult != null) {
                // aggregation() metodundan sonra getAggregate() diyerek asıl veriye ulaşıyoruz
                Aggregate aggregate = depResult.aggregation().getAggregate();
                if (aggregate.isSterms()) {
                    for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                        depFilters.put(bucket.key().stringValue(), bucket.docCount());
                    }
                }
            }

            // Arrival Filtreleme
            var arrResult = esAggs.get("arrival_counts");
            if (arrResult != null) {
                Aggregate aggregate = arrResult.aggregation().getAggregate();
                if (aggregate.isSterms()) {
                    for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                        arrFilters.put(bucket.key().stringValue(), bucket.docCount());
                    }
                }
            }
        }

        return FlightSearchResponseDTO.builder()
                .flights(flights)
                .departureCityFilters(depFilters)
                .arrivalCityFilters(arrFilters)
                .totalFound(searchHits.getTotalHits())
                .build();
    }



    public List<FlightIndex> getAllFlights() {
        Iterable<FlightIndex> all = flightSearchRepository.findAll();
        List<FlightIndex> flightList = new ArrayList<>();
        all.forEach(flightList::add);
        return flightList;
    }

// build ettiğin an biter işlem

 //   NativeQuery myquer=NativeQuery.builder().withQuery(m->m.)


}