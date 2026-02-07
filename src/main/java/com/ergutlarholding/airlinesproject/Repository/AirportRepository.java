package com.ergutlarholding.airlinesproject.Repository;

import com.ergutlarholding.airlinesproject.Entity.Airport;
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