package com.ergutlarholding.airlinesproject.Repository;

import com.ergutlarholding.airlinesproject.Entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    // TCKN ile yolcu sorgulama (En çok kullanacağın sorgu budur)
    java.util.Optional<Passenger> findByTckn(String tckn);

    // Mail adresine göre yolcu bulma
    java.util.Optional<Passenger> findByMail(String mail);
}