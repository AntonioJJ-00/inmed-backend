package com.inmed.pos.entity;

import com.inmed.branch.entity.Branch;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "pos_devices",
        indexes = {
                @Index(name = "idx_pos_code", columnList = "code"),
                @Index(name = "idx_pos_serial", columnList = "serial_number")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Sucursal propietaria
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Branch branch;

    /**
     * Código interno
     * POS-001
     */
    @Column(nullable = false, length = 30)
    private String code;

    /**
     * Nombre visible
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Identificador único
     */
    @Column(
            name = "serial_number",
            nullable = false,
            unique = true,
            length = 100
    )
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PosStatus status;

    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;

    @Column(name = "app_version", length = 20)
    private String appVersion;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 50)
    private String createdBy;

    @Column(length = 50)
    private String updatedBy;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (enabled == null)
            enabled = true;

        if (status == null)
            status = PosStatus.ACTIVE;
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }

    public void setLastIp(String ipAddress) {
    }
}