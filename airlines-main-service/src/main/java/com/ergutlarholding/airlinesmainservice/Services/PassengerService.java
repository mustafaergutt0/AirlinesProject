package com.ergutlarholding.airlinesmainservice.Services;

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

    public PassengerResponse savePassenger(PassengerRequest request, Long authId) {
        Passenger passenger = passengerMapper.toEntity(request);
        passenger.setAuthId(authId); // Köprüyü kurduk
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



    @Transactional
    public PassengerResponse updatePassenger(Long id, PassengerRequest request) {
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek yolcu bulunamadı!"));

        // Manuel setter'lar bitti, MapStruct her şeyi tek satırda güncelledi
        passengerMapper.updateEntityFromDto(request, existingPassenger);

        return passengerMapper.toResponse(passengerRepository.save(existingPassenger));
    }

    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new RuntimeException("Silinecek yolcu bulunamadı!");
        }
        passengerRepository.deleteById(id);
    }
}