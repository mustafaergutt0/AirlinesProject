package com.ergutlarholding.authservice.Client;
import com.ergutlarholding.authservice.Dto.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;





@FeignClient(name = "PILOT-SERVICE")
public interface PilotClient {
    // URL'e dikkat: Pilot serviste @RequestMapping("/pilots") demiştin
    @PostMapping("/pilots/save")
    void savePilot(@RequestBody Object request, @RequestParam("authId") Long authId);
}