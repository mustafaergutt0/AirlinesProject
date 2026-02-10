package com.ergutlarholding.airlinesmainservice.Repository;

import com.ergutlarholding.airlinesmainservice.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    java.util.Optional<Flight> findByFlightCode(String flightCode);
}