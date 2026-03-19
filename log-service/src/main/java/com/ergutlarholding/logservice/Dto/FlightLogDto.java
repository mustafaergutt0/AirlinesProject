package com.ergutlarholding.logservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightLogDto implements Serializable {
    private String serviceName;
    private String message;
    private String timestamp;
}
