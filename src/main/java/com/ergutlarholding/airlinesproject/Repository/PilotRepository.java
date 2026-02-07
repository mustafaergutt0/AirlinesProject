package com.ergutlarholding.airlinesproject.Repository;

import com.ergutlarholding.airlinesproject.Entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Long> {
    Optional<Pilot> findByMail(String mail);  // optinoal olmassa ise orelse methotu çalışmaz optionla da null gelebilr demke
}