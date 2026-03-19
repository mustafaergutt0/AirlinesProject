package com.ergutlarholding.airlinesmainservice.Dto.Flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor

@AllArgsConstructor
@ToString

public class FlightLogDto  implements Serializable {
    private String serviceName;
    private String message;
    private String timestamp;
}
