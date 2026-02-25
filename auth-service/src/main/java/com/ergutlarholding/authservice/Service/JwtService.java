package com.ergutlarholding.authservice.Service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    // Gerçek projede bunu application.yml'den almalısın
    private static final String SECRET_KEY = "bu-cok-gizli-ve-uzun-bir-anahtar-olmali-en-az-256-bit";

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Token içine rolü gömüyoruz ki diğer servisler bilsin

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 saatlik ömür
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Decode etmeden direkt byte dizisine çeviren bu yöntemi kullan:
    private Key getSignInKey() {
        // Eski hatalı kod: byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}