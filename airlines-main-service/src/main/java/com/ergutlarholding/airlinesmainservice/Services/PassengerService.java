package com.ergutlarholding.airlinesmainservice.Services;

import com.ergutlarholding.airlinesmainservice.Dto.Passenger.LoginRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Passenger;
import com.ergutlarholding.airlinesmainservice.Mapper.PassengerMapper;
import com.ergutlarholding.airlinesmainservice.Repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper; // MapStruct yardımcımız

    public PassengerResponse savePassenger(PassengerRequest request) {
        // Builder bitti, Mapper geldi
        Passenger passenger = passengerMapper.toEntity(request);
        return passengerMapper.toResponse(passengerRepository.save(passenger));
    }

    public List<PassengerResponse> getAllPassengers() {
        // Stream().collect() bitti, tek satır geldi
        return passengerMapper.toResponseList(passengerRepository.findAll());
    }

    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yolcu bulunamadı! ID: " + id));
        return passengerMapper.toResponse(passenger);
    }

    public String login(LoginRequest loginRequest) {
        Passenger passenger = passengerRepository.findByMail(loginRequest.mail())
                .orElseThrow(() -> new RuntimeException("E-posta bulunamadı!"));

        if (passenger.getPassword().equals(loginRequest.password())) {
            return "Giriş Başarılı! Hoş geldin " + passenger.getName();
        } else {
            throw new RuntimeException("Hatalı şifre!");
        }
    }

    @Transactional
    public PassengerResponse updatePassenger(Long id, PassengerRequest request) {
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek yolcu bulunamadı!"));

        // Manuel setter'lar bitti, MapStruct her şeyi tek satırda güncelledi
        passengerMapper.updatePassengerFromRequest(request, existingPassenger);

        return passengerMapper.toResponse(passengerRepository.save(existingPassenger));
    }

    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new RuntimeException("Silinecek yolcu bulunamadı!");
        }
        passengerRepository.deleteById(id);
    }
}