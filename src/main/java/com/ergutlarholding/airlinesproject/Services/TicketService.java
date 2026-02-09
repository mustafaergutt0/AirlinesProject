package com.ergutlarholding.airlinesproject.Services;

import com.ergutlarholding.airlinesproject.Dto.Ticket.TicketRequest;
import com.ergutlarholding.airlinesproject.Dto.Ticket.TicketResponse;
import com.ergutlarholding.airlinesproject.Entity.Flight;
import com.ergutlarholding.airlinesproject.Entity.Passenger;
import com.ergutlarholding.airlinesproject.Entity.Ticket;
import com.ergutlarholding.airlinesproject.Mapper.TicketMapper;
import com.ergutlarholding.airlinesproject.Repository.FlightRepository;
import com.ergutlarholding.airlinesproject.Repository.PassengerRepository;
import com.ergutlarholding.airlinesproject.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final TicketMapper ticketMapper; // MapStruct enjeksiyonu

    public TicketResponse buyTicket(TicketRequest request) {
        // 1. Uçuş ve Yolcu var mı kontrol et (Senin mantığın)
        Flight flight = flightRepository.findById(request.flightId())
                .orElseThrow(() -> new RuntimeException("Hata: Uçuş bulunamadı!"));

        Passenger passenger = passengerRepository.findById(request.passengerId())
                .orElseThrow(() -> new RuntimeException("Hata: Yolcu bulunamadı!"));

        // 2. Bileti oluştur ve kaydet
        Ticket ticket = Ticket.builder()
                .seatNumber(request.seatNumber())
                .ticketClass(request.ticketClass())
                .flight(flight)
                .passenger(passenger)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        // 3. Response'a çevirip gönder (Burada senin mapToResponse ismini mapper ile bağlıyoruz)
        return mapToResponse(savedTicket);
    }

    public List<TicketResponse> getAllTickets() {
        return ticketMapper.toResponseList(ticketRepository.findAll());
    }

    // Senin Helper Metodun: Artık MapStruct ile çalışıyor
    private TicketResponse mapToResponse(Ticket ticket) {
        return ticketMapper.toResponse(ticket);
    }

    public String deleteTicket(Long id) {
        // 1. Önce bilet gerçekten var mı kontrol et
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İptal edilmek istenen bilet bulunamadı! ID: " + id));

        // 2. Bileti veritabanından sil
        ticketRepository.delete(ticket);

        // 3. Bilgi mesajı dön (Senin orijinal mesaj formatın)
        return ticket.getPassenger().getName() + " isimli yolcuya ait " +
                ticket.getSeatNumber() + " numaralı bilet iptal edildi.";
    }
}