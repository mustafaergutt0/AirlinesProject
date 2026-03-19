package com.ergutlarholding.logservice.Repository; // Büyük-küçük harfe dikkat!

import com.ergutlarholding.logservice.Entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
}