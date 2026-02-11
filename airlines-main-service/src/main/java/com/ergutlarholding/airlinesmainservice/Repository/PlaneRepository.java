package com.ergutlarholding.airlinesmainservice.Repository;

import com.ergutlarholding.airlinesmainservice.Entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {
}