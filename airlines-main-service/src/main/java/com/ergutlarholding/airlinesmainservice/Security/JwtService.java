package com.ergutlarholding.airlinesmainservice.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Auth-Service ile AYNI secret olmalı
    private final String SECRET = "bu-cok-gizli-ve-uzun-bir-anahtar-olmali-en-az-256-bit";

    // Username (sub)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ sid claim'ini çıkar
    public String extractSid(String token) {
        return extractAllClaims(token).get("sid", String.class);
    }

    // Token geçerli mi? (imza + exp)
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims); // ✅ HATASIZ
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}