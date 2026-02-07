package com.ergutlarholding.airlinesproject.Repository;

import com.ergutlarholding.airlinesproject.Entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {
}