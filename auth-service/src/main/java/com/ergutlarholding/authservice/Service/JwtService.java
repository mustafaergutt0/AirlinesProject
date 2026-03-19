package com.ergutlarholding.authservice.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "bu-cok-gizli-ve-uzun-bir-anahtar-olmali-en-az-256-bit";

    // ✅ sessionId (sid) artık JWT içine gömülüyor
    public String generateToken(String username, String role, String sessionId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("sid", sessionId); // ✅ kritik satır

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60)) // 1 saat
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}