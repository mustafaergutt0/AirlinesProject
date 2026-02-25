package com.ergutlarholding.authservice.Repository;

import com.ergutlarholding.authservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Giriş yaparken kullanıcıyı mail adresiyle bulmamız gerekecek
    Optional<User> findByMail(String mail);
}