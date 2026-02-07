package com.ergutlarholding.airlinesproject.Servis;

import com.ergutlarholding.airlinesproject.Dto.Passenger.LoginRequest;
import com.ergutlarholding.airlinesproject.Dto.Passenger.PassengerRequest;
import com.ergutlarholding.airlinesproject.Dto.Passenger.PassengerResponse;
import com.ergutlarholding.airlinesproject.Entity.Passenger;
import com.ergutlarholding.airlinesproject.Repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerService {


    @Autowired
    private final PassengerRepository passengerRepository;

    // 1. Yolcu Kaydetme (Request -> Entity -> Response)
    public PassengerResponse savePassenger(PassengerRequest request) {
        // Builder pattern ile Request'ten Entity'ye çeviriyoruz
        Passenger passenger = Passenger.builder()
                .name(request.name())
                .surname(request.surname())
                .tckn(request.tckn())
                .mail(request.mail())
                .password(request.password()) // sonradan ekledik unutmuşuz
                .gsm(request.gsm())
                .gender(request.gender())
                .birthDate(request.birthDate())
                .build();

        Passenger savedPassenger = passengerRepository.save(passenger);

        // Kaydedilen Entity'yi Response Record'una çevirip dönüyoruz
        return mapToResponse(savedPassenger);
    }

    // 2. Tüm Yolcuları Getirme (Entity List -> Response List)
    public List<PassengerResponse> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 3. Tek Yolcu Getirme

    // PassengerResponse olarak dönmek için methotlar aşagıda

    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yolcu bulunamadı! ID: " + id));
        return mapToResponse(passenger);
    }


    // PassengerService.java içine eklenecek basit login kontrolü
    public String login(LoginRequest loginRequest) {
        Passenger passenger = passengerRepository.findByMail(loginRequest.mail())
                .orElseThrow(() -> new RuntimeException("E-posta bulunamadı!"));

        if (passenger.getPassword().equals(loginRequest.password())) {
            return "Giriş Başarılı! Hoş geldin " + passenger.getName();
        } else {
            throw new RuntimeException("Hatalı şifre!");
        }
    }

    // Helper Metod: Entity'den Response Record'una dönüşüm (Mutfaktan Masaya)
    private PassengerResponse mapToResponse(Passenger passenger) {
        return new PassengerResponse(
                passenger.getId(),
                passenger.getName(),
                passenger.getSurname(),
                passenger.getTckn(),
                passenger.getMail(),
                passenger.getGsm(),
                passenger.getBirthDate()
        );
    }

    public PassengerResponse updatePassenger(Long id, PassengerRequest request) {
        // Önce yolcu var mı diye kontrol et
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek yolcu bulunamadı!"));

        // Bilgileri güncelle (Builder yerine setter veya manuel atama)
        existingPassenger.setName(request.name());
        existingPassenger.setSurname(request.surname());
        existingPassenger.setMail(request.mail());
        existingPassenger.setGsm(request.gsm());
        // Şifre güncellemesi genellikle ayrı yapılır ama buraya da eklenebilir

        Passenger updatedPassenger = passengerRepository.save(existingPassenger);
        return mapToResponse(updatedPassenger);
    }

    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new RuntimeException("Silinecek yolcu bulunamadı!");
        }
        passengerRepository.deleteById(id);
    }



}