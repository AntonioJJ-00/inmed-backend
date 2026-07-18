package com.inmed.auth.repository;

import com.inmed.auth.entity.RefreshToken;
import com.inmed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    @Query("""
            SELECT rt
            FROM RefreshToken rt
            JOIN FETCH rt.user
            WHERE rt.token = :token
            """)
    Optional<RefreshToken> findByTokenWithUser(
            @Param("token") String token
    );

    @Query("""
            SELECT rt
            FROM RefreshToken rt
            JOIN FETCH rt.user
            WHERE rt.user = :user
            """)
    List<RefreshToken> findByUser(
            @Param("user") User user
    );

    @Query("""
            SELECT rt
            FROM RefreshToken rt
            JOIN FETCH rt.user
            """)
    List<RefreshToken> findAllWithUser();

    @Query("""
            SELECT rt
            FROM RefreshToken rt
            JOIN FETCH rt.user
            WHERE rt.revoked = false
            AND rt.expiryDate > CURRENT_TIMESTAMP
            """)
    List<RefreshToken> findActiveSessions();

    @Query("""
            SELECT rt
            FROM RefreshToken rt
            JOIN FETCH rt.user
            WHERE rt.user = :user
            AND rt.revoked = false
            AND rt.expiryDate > CURRENT_TIMESTAMP
            """)
    List<RefreshToken> findActiveSessionsByUser(
            @Param("user") User user
    );

    @Query("""
            SELECT COUNT(rt)
            FROM RefreshToken rt
            WHERE rt.user = :user
            AND rt.revoked = false
            AND rt.expiryDate > CURRENT_TIMESTAMP
            """)
    long countActiveSessions(
            @Param("user") User user
    );

    Optional<RefreshToken> findBySessionId(
            String sessionId
    );

    @Query("""
            SELECT rt
            FROM RefreshToken rt
            WHERE rt.familyId = :familyId
            """)
    List<RefreshToken> findByFamilyId(
            @Param("familyId") String familyId
    );

    @Query("""
            SELECT rt
            FROM RefreshToken rt
            WHERE rt.expiryDate < :date
            """)
    List<RefreshToken> findExpired(
            @Param("date") LocalDateTime date
    );

    void deleteByExpiryDateBefore(
            LocalDateTime date
    );
}
