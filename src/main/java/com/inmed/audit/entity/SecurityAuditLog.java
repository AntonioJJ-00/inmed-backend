package com.inmed.audit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "audit_logs",
        indexes = {

                @Index(
                        name = "idx_audit_username",
                        columnList = "username"
                ),

                @Index(
                        name = "idx_audit_event",
                        columnList = "event"
                ),

                @Index(
                        name = "idx_audit_created",
                        columnList = "createdAt"
                )

        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityAuditLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /**
     * Usuario que genera el evento
     */
    @Column(
            nullable = false,
            length = 100
    )
    private String username;



    /**
     * Tipo de evento de seguridad
     *
     * LOGIN_SUCCESS
     * LOGIN_FAILED
     * LOGOUT
     * REFRESH_TOKEN_USED
     * SESSION_REVOKED
     */
    @Column(
            nullable = false,
            length = 50
    )
    private String event;



    /**
     * IP origen
     */
    @Column(
            length = 100
    )
    private String ipAddress;



    /**
     * Navegador / dispositivo
     */
    @Column(
            length = 255
    )
    private String device;



    /**
     * Sesión relacionada
     */
    @Column(
            length = 100
    )
    private String sessionId;



    /**
     * Información adicional
     *
     * Ejemplo:
     *
     * {
     *   "role":"ADMIN",
     *   "client":"WEB"
     * }
     *
     */
    @Column(
            columnDefinition = "TEXT"
    )
    private String metadata;



    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;



    @PrePersist
    protected void onCreate(){

        this.createdAt =
                LocalDateTime.now();

    }

}