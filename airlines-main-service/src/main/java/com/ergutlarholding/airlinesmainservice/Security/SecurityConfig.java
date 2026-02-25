package com.ergutlarholding.airlinesmainservice.Security;

import com.ergutlarholding.airlinesmainservice.Security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // 1. HERKESE AÇIK (Giriş Şartı Yok)
                        .requestMatchers(HttpMethod.GET, "/main/airports/**", "/main/flights/getAllFlights", "/main/planes/**").permitAll()

                        // 2. SADECE ADMIN (Havalimanı, Uçak ve Uçuş Yönetimi)
                        // hasRole kullanırken "ROLE_" kısmını Spring kendi yönetir,
                        // ama biz Enum'da tam ismi verdiğimiz için hasAuthority daha garantidir veya hasRole("ADMIN") yeterlidir.
                        .requestMatchers("/main/airports/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/main/planes/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/main/flights/create", "/main/flights/DeleteAll").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/main/flights/**").hasAuthority("ROLE_ADMIN")

                        // 3. YOLCU VE DİĞERLERİ
                        .requestMatchers("/main/tickets/buy").hasAnyAuthority("ROLE_PASSENGER", "ROLE_ADMIN")
                        .requestMatchers("/main/passengers/save").permitAll() // Login olan herkes profil oluşturabilsin

                        // 4. GERİ KALANLAR
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}