package com.inmed.inventory.repository;


import com.inmed.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {


    /**
     * Validar que un producto
     * no esté duplicado dentro
     * de la misma sucursal
     */
    boolean existsByBranchIdAndProductId(
            Long branchId,
            Long productId
    );



    /**
     * Obtener inventario completo
     * de una sucursal
     */
    List<Inventory> findByBranchId(
            Long branchId
    );



    /**
     * Obtener inventario de un producto
     * en todas las sucursales
     */
    List<Inventory> findByProductId(
            Long productId
    );



    /**
     * Buscar producto especifico
     * dentro de una sucursal
     */
    Optional<Inventory> findByBranchIdAndProductId(
            Long branchId,
            Long productId
    );



    /**
     * Sync incremental
     *
     * Busca registros modificados
     * después de una versión determinada
     */
    List<Inventory> findByVersionGreaterThan(
            Long version
    );



    /**
     * Inventarios activos
     */
    List<Inventory> findByEnabledTrue();


}