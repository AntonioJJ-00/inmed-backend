package com.inmed.PosCredential.entity;

import com.inmed.pos.entity.PosDevice;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "pos_credentials",
        indexes = {
                @Index(
                        name = "idx_pos_username",
                        columnList = "username"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * POS asociado
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pos_id",
            nullable = false,
            unique = true
    )
    private PosDevice posDevice;

    /**
     * Usuario utilizado por el POS
     */
    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;

    /**
     * Password cifrado
     */
    @Column(
            nullable = false,
            length = 255
    )
    private String password;

    /**
     * Permite bloquear el POS
     */
    @Column(nullable = false)
    private Boolean enabled;

    /**
     * Último login exitoso
     */
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /**
     * Último heartbeat recibido
     */
    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;

    /**
     * Versión instalada del software
     */
    @Column(name = "app_version", length = 20)
    private String appVersion;

    /**
     * Dirección IP del último acceso
     */
    @Column(name = "last_ip", length = 50)
    private String lastIp;

    /**
     * Auditoría
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (enabled == null) {
            enabled = true;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}