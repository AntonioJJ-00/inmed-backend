package com.inmed.category.entity;

import com.inmed.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(
                        name = "idx_category_code",
                        columnList = "code"
                ),
                @Index(
                        name = "idx_category_name",
                        columnList = "name"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código interno único.
     *
     * Ejemplo:
     * MED
     * LIM
     * PAP
     */
    @Column(
            nullable = false,
            unique = true,
            length = 30
    )
    private String code;

    /**
     * Nombre visible.
     */
    @Column(
            nullable = false,
            length = 100
    )
    private String name;

    /**
     * Descripción opcional.
     */
    @Column(length = 255)
    private String description;

    /**
     * Estado.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus status;

    /**
     * Empresa propietaria.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    private Company company;

    /**
     * Versión utilizada por el Motor Sync.
     *
     * Cada modificación incrementará este valor.
     */
    @Column(nullable = false)
    private Long syncVersion;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = CategoryStatus.ACTIVE;
        }

        if (syncVersion == null) {
            syncVersion = 1L;
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

        if (syncVersion == null) {
            syncVersion = 1L;
        } else {
            syncVersion++;
        }

    }

}