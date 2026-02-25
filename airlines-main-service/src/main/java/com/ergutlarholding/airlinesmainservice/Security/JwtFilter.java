package com.ergutlarholding.airlinesmainservice.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate; // Eklendi
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate; // Redis bağlantısı eklendi

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // 1. Bearer Token kontrolü
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        // 2. KRİTİK ADIM: Redis'ten oturum bilgilerini çekiyoruz
        // Auth-Service login anında bu token'ı key, bilgileri Map (Value) olarak kaydetmişti.
        Map<String, Object> sessionData = (Map<String, Object>) redisTemplate.opsForValue().get(jwt);

        // Eğer Redis'te bu token yoksa (Logout olunmuş veya süre bitmiş), geçişi engelle!
        if (sessionData == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Oturum geçersiz veya sona ermiş. Lütfen tekrar giriş yapın.");
            return;
        }

        // 3. Bilgileri Redis'teki Map'ten alıyoruz (DB'ye veya JWT'yi tekrar çözmeye gerek kalmadı)
        String userEmail = (String) sessionData.get("email");
        String role = (String) sessionData.get("role");

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // JWT imza kontrolü (Güvenlik için çift dikiş)
            if (jwtService.isTokenValid(jwt)) {

                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        authorities
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Spring Security bağlamına kullanıcıyı yerleştir
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}