package com.ergutlarholding.authservice.Service;

import com.ergutlarholding.authservice.Client.MainClient;
import com.ergutlarholding.authservice.Client.PilotClient;
import com.ergutlarholding.authservice.Dto.LoginRequest;
import com.ergutlarholding.authservice.Dto.RegisterRequest;
import com.ergutlarholding.authservice.Entity.Role;
import com.ergutlarholding.authservice.Entity.User;
import com.ergutlarholding.authservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j // Loglama için eklendi
public class AuthService {

    private final UserRepository userRepository;
    private final MainClient mainClient;
    private final PilotClient pilotClient;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService  jwtService;
    // AuthService.java içinde mevcut fieldların yanına ekle:
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public String register(RegisterRequest request) {
        log.info("Yeni kayıt isteği geldi: {} - Rol: {}", request.getMail(), request.getRole());

        // 1. Şifreyi BCrypt ile güvenli hale getir
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 2. Enum dönüşümünü güvenli yap (ROLE_ prefix'ine bakmaksızın)
        Role userRole;
        try {
            // Kullanıcıdan "PILOT" gelse de "ROLE_PILOT" gelse de doğru Enum'ı bulur
            String roleStr = request.getRole().toUpperCase();
            if (!roleStr.startsWith("ROLE_")) {
                roleStr = "ROLE_" + roleStr;
            }
            userRole = Role.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Geçersiz rol tipi: " + request.getRole());
        }

        // 3. Auth DB'sine kaydet
        User user = User.builder()
                .mail(request.getMail())
                .password(encodedPassword)
                .role(userRole)
                .build();

        User savedUser = userRepository.save(user);
        log.info("Auth DB kaydı başarılı. User ID: {}", savedUser.getId());

        // 4. Dağıtık veri kaydı (Orchestration)
        try {
            // ARTIK KONTROLÜ ENUM ÜZERİNDEN YAPIYORUZ
            if (user.getRole() == Role.ROLE_PILOT) {
                log.info("Pilot-Service'e yönlendiriliyor...");
                pilotClient.savePilot(request, savedUser.getId());
            } else {
                log.info("Main-Service'e (Yolcu) yönlendiriliyor...");
                mainClient.savePassenger(request, savedUser.getId());
            }
        } catch (Exception e) {
            log.error("Servisler arası iletişim koptu! İşlem geri alınıyor. Hata: {}", e.getMessage());
            // @Transactional sayesinde buradaki hata User kaydını da DB'den siler (Rollback)
            throw new RuntimeException("Mikroservis iletişim hatası: " + e.getMessage());
        }

        return "Kurumsal kayıt başarıyla tamamlandı. User ID: " + savedUser.getId();
    }


    public String login(LoginRequest request) {

        User user = userRepository.findByMail(request.getMail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Hatalı şifre!");
        }

        // ✅ 1) Redis için ayrı unique sessionId üret
        String sessionId = UUID.randomUUID().toString();
        String redisKey = "sess:" + sessionId;

        // ✅ 2) Redis'e JWT ile ilgili hiçbir şey yazma (Map yaz)
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("authId", user.getId());
        sessionData.put("email", user.getMail());
        sessionData.put("role", user.getRole().name());
        sessionData.put("createdAt", System.currentTimeMillis());

        // TTL: JWT exp ile aynı olsun (1 saat)
        redisTemplate.opsForValue().set(redisKey, sessionData, 1, TimeUnit.HOURS);

        // ✅ 3) JWT üret: içine sid = sessionId gömülür
        String token = jwtService.generateToken(user.getMail(), user.getRole().name(), sessionId);
        // jwt sistemde gezecek ama jwt içnideki session ıd ile rediste session ıd gçerli olursa
        // jwt işlem görecek


        log.info("Kullanıcı oturumu Redis'e kaydedildi. redisKey={}, email={}", redisKey, user.getMail());

        return token;
    }


}