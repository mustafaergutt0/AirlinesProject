package com.ergutlarholding.airlinesproject.Controller.TicketController;

import com.ergutlarholding.airlinesproject.Dto.Ticket.TicketRequest;
import com.ergutlarholding.airlinesproject.Dto.Ticket.TicketResponse;
import com.ergutlarholding.airlinesproject.Services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // Yeni Bilet Satın Al
    @PostMapping("/buy")
    public ResponseEntity<TicketResponse> buyTicket(@RequestBody TicketRequest request) {// içine ne verirsen onu return edersin
        // response entiy .ok diyerek ilerler
        return ResponseEntity.ok(ticketService.buyTicket(request));
    }

    // Sistemdeki Tüm Biletleri Gör
    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAll() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.deleteTicket(id));
    }


}