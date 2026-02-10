package com.ergutlarholding.airlinesmainservice.Repository;

import com.ergutlarholding.airlinesmainservice.Entity.Airport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCode(String code);

    // Silme işlemi için transactional eklemek şarttır
    @Transactional
    void deleteByCode(String code);
}