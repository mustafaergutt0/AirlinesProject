package com.ergutlarholding.authservice.Client;


import com.ergutlarholding.authservice.Dto.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AIRLINES-MAIN-SERVICE")
public interface MainClient {
    // URL'e dikkat: Main serviste @RequestMapping("/main/passengers") demiştin
    @PostMapping("/main/passengers/save")
    void savePassenger(@RequestBody Object request, @RequestParam("authId") Long authId);
}
