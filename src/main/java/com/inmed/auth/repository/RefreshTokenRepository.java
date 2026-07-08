package com.inmed.auth.repository;

import com.inmed.auth.entity.RefreshToken;
import com.inmed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);

    void deleteByToken(String token);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);

    // ✅ FIX PROFESIONAL: evita LazyInitializationException
    @Query("""
        SELECT rt FROM RefreshToken rt
        JOIN FETCH rt.user
        WHERE rt.token = :token
    """)
    Optional<RefreshToken> findByTokenWithUser(@Param("token") String token);

    // FIX para sesiones
    @Query("""
        SELECT rt FROM RefreshToken rt
        JOIN FETCH rt.user
    """)
    List<RefreshToken> findAllWithUser();
}