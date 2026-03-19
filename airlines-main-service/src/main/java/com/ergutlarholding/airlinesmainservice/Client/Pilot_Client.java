package com.ergutlarholding.airlinesmainservice.Client;

import com.ergutlarholding.airlinesmainservice.Client.Dto.PilotResponse; // Doğru paket!
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PILOT-SERVICE")
public interface Pilot_Client {

    @GetMapping("/pilots/{id}")
    PilotResponse getPilotById(@PathVariable("id") Long id);

}