package com.inmed.auth.entity;

import com.inmed.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(
                        name = "idx_refresh_token",
                        columnList = "token"
                ),
                @Index(
                        name = "idx_refresh_user",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
            nullable = false,
            unique = true,
            length = 500
    )
    private String token;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="user_id",
            nullable=false
    )
    private User user;


    @Column(nullable=false)
    private LocalDateTime expiryDate;


    @Column(nullable=false)
    private boolean revoked;

    private String sessionId;

    private String clientType;

    private String userAgent;

    /**
     * Familia del refresh token.
     * Sirve para detectar robo de tokens.
     */
    @Column(
            nullable=false,
            length=100
    )
    private String familyId;


    /**
     * Version del refresh token dentro de la familia
     */
    @Column(nullable=false)
    private Integer version;


    @Column(
            updatable=false
    )
    private LocalDateTime createdAt;


    private LocalDateTime revokedAt;


    /**
     * Información del cliente
     * navegador, app móvil, etc.
     */
    private String deviceInfo;


    /**
     * IP desde donde inició sesión
     */
    private String ipAddress;


    /**
     * Último uso del refresh token
     */
    private LocalDateTime lastUsedAt;



    @PrePersist
    protected void onCreate(){

        this.createdAt =
                LocalDateTime.now();


        this.revoked=false;


        if(this.version==null){
            this.version=1;
        }

    }

}