package com.inmed.product.repository;

import com.inmed.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long> {
    /*
     * Validación para evitar productos duplicados
     */
    boolean existsByCode(String code);
    /*
     * Consulta individual por código
     */
    Optional<Product> findByCode(String code);
    /*
     * Productos activos para administración
     */
    List<Product> findByActiveTrue();
    /*
     * Productos modificados después
     * de una versión de sincronización
     *
     * Ejemplo:
     *
     * POS tiene syncVersion 10
     *
     * Cloud devuelve:
     *
     * productos > 10
     *
     */
    List<Product> findBySyncVersionGreaterThan(
            Integer version
    );
    /*
     * Productos pertenecientes a una empresa
     */
    List<Product> findByCompanyId(
            Long companyId
    );
}