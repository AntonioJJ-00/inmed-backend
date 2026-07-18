package com.inmed.inventory.entity;

import com.inmed.branch.entity.Branch;
import com.inmed.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "inventories",
        indexes = {
                @Index(
                        name = "idx_inventory_branch",
                        columnList = "branch_id"
                ),
                @Index(
                        name = "idx_inventory_product",
                        columnList = "product_id"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Sucursal donde existe el inventario
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "branch_id",
            nullable = false
    )
    private Branch branch;
    /**
     * Producto relacionado
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;
    /**
     * Cantidad disponible
     */
    @Column(
            nullable = false
    )
    private Integer stock;
    /**
     * Producto reservado
     * Ejemplo:
     * apartados
     * pedidos pendientes
     */
    @Column(
            nullable = false
    )
    private Integer reservedStock;
    /**
     * Punto mínimo para alertas
     */
    private Integer minimumStock;
    /**
     * Máximo permitido
     */
    private Integer maximumStock;
    /**
     * Última modificación del inventario
     */
    private LocalDateTime lastMovement;
    /**
     * Última sincronización Cloud
     */
    private LocalDateTime lastSync;
    /**
     * Control incremental Sync
     */
    @Version
    private Long version;

    @Column(
            nullable = false
    )
    private Boolean enabled;

    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void create(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if(stock == null){
            stock = 0;
        }
        if(reservedStock == null){
            reservedStock = 0;
        }
        if(enabled == null){
            enabled = true;
        }
    }

    @PreUpdate
    public void update(){
        updatedAt = LocalDateTime.now();
    }
}