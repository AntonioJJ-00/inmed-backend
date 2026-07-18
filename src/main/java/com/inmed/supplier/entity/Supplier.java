package com.inmed.supplier.entity;

import com.inmed.company.entity.Company;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "suppliers",
        indexes = {
                @Index(
                        name = "idx_supplier_code",
                        columnList = "code"
                ),
                @Index(
                        name = "idx_supplier_name",
                        columnList = "name"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código interno del proveedor.
     * <p>
     * Ejemplo:
     * <p>
     * PFIZER
     * DISTR001
     */
    @Column(
            nullable = false,
            unique = true,
            length = 30
    )
    private String code;

    /**
     * Nombre comercial
     */
    @Column(
            nullable = false,
            length = 150
    )
    private String name;

    /**
     * RFC / Tax ID opcional
     */
    @Column(
            length = 30
    )
    private String taxId;

    /**
     * Persona de contacto
     */
    @Column(
            length = 100
    )
    private String contactName;

    @Column(
            length = 30
    )
    private String phone;

    @Column(
            length = 150
    )
    private String email;

    @Column(
            length = 255
    )
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false
    )
    private SupplierStatus status;

    /**
     * Empresa propietaria del catálogo
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    private Company company;

    /**
     * Control incremental Sync
     */
    @Column(
            nullable = false
    )
    private Long syncVersion;

    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if(status == null){
            status = SupplierStatus.ACTIVE;
        }
        if(syncVersion == null){
            syncVersion = 1L;
        }
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
        if(syncVersion == null){
            syncVersion = 1L;
        }
        else{
            syncVersion++;
        }
    }
}