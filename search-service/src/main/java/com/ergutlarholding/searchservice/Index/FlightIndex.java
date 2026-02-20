package com.ergutlarholding.searchservice.Index;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "newflights")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightIndex {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String flightCode;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String departureCity;

    @Field(type = FieldType.Keyword)
    private String arrivalCity;

    @Field(type = FieldType.Keyword)
    private String departureAirportName;

    @Field(type = FieldType.Keyword)
    private String arrivalAirportName;

    // BURAYI DÜZELTTİK: FieldType sadece Date olmalı
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime departureTime;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime arrivalTime;
}