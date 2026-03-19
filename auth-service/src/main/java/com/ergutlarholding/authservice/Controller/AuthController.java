package com.ergutlarholding.authservice.Controller;

import com.ergutlarholding.authservice.Dto.LoginRequest;
import com.ergutlarholding.authservice.Dto.RegisterRequest;
import com.ergutlarholding.authservice.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        // Servis katmanındaki o "Master Register" metodunu çağırıyoruz
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    // Test amaçlı: Servisin ayakta olup olmadığını anlamak için
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Auth-Service is UP and Running!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}