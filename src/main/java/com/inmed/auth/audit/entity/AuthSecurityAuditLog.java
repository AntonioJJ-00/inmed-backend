package com.inmed.auth.audit.entity;

import com.inmed.auth.audit.enums.SecurityEventType;
import com.inmed.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "security_audit_logs",
        indexes = {

                @Index(
                        name = "idx_security_audit_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_security_audit_event",
                        columnList = "event_type"
                ),

                @Index(
                        name = "idx_security_audit_date",
                        columnList = "created_at"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSecurityAuditLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /**
     * Usuario que realizó la acción
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id"
    )
    private User user;



    /**
     * Usuario en texto para conservar
     * historial aunque el usuario cambie
     */
    @Column(
            nullable = false,
            length = 100
    )
    private String username;



    /**
     * Tipo de evento de seguridad
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private SecurityEventType eventType;

    /**
     * Dirección IP del cliente
     */
    @Column(
            length = 100
    )
    private String ipAddress;



    /**
     * Navegador, dispositivo o aplicación
     */
    @Column(
            length = 500
    )
    private String device;



    /**
     * Identificador de sesión
     */
    @Column(
            length = 100
    )
    private String sessionId;



    /**
     * Información adicional del evento
     *
     * Ejemplo:
     *
     * {
     *   "reason":"ADMIN_FORCE_LOGOUT",
     *   "oldRole":"USER",
     *   "newRole":"ADMIN"
     * }
     */
    @Column(
            columnDefinition = "TEXT"
    )
    private String metadata;
    /**
     * Fecha del evento
     */
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