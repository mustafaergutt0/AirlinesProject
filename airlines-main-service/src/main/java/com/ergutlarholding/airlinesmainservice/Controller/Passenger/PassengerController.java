package com.ergutlarholding.airlinesmainservice.Controller.Passenger;

import com.ergutlarholding.airlinesmainservice.Dto.Passenger.LoginRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerResponse;
import com.ergutlarholding.airlinesmainservice.Services.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor  // bu bizim yerimize DI yaptı
public class PassengerController {


    private final PassengerService passengerService;

    // Yeni Yolcu Kaydı (Register)
    @PostMapping("/save")
    public ResponseEntity<PassengerResponse> register(@RequestBody PassengerRequest request) {
        return ResponseEntity.ok(passengerService.savePassenger(request));
    }

    // Giriş Yapma (Login)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(passengerService.login(loginRequest));
    }

    // Tüm Yolcuları Getir (Sadece test amaçlı, normalde güvenli olmalı)
    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAll() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    // ID ile Yolcu Getir
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @PutMapping("guncelle/{id}")
    public ResponseEntity<PassengerResponse> update(@PathVariable Long id, @RequestBody PassengerRequest request) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, request));
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build(); // 204 No Content döner // 204 dönüyor ve hiç bir şey yok
    }



}