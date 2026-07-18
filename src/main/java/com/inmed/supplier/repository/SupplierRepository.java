package com.inmed.supplier.repository;

import com.inmed.supplier.entity.Supplier;
import com.inmed.supplier.entity.SupplierStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository
        extends JpaRepository<Supplier, Long> {
    /**
     * Validar código único del proveedor
     */
    boolean existsByCode(String code);
    /**
     * Buscar proveedor por código
     */
    Optional<Supplier> findByCode(String code);
    /**
     * Proveedores de una empresa
     */
    List<Supplier> findByCompanyId(Long companyId);
    /**
     * Catálogo activo
     */
    List<Supplier> findByStatus(
            SupplierStatus status
    );
    /**
     * Para sincronización futura
     *
     * Obtener cambios posteriores
     */
    List<Supplier> findBySyncVersionGreaterThan(
            Long version
    );
}