package com.ergutlarholding.pilotservice.Repository;

import com.ergutlarholding.pilotservice.Entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Long> {
    Optional<Pilot> findByMail(String mail);
}