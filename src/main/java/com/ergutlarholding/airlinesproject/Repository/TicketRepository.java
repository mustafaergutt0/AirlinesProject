package com.ergutlarholding.airlinesproject.Repository;

import com.ergutlarholding.airlinesproject.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Bir uçuşa ait tüm biletleri listelemek için
    java.util.List<Ticket> findByFlightId(Long flightId);
}