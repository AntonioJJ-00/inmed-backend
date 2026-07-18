package com.inmed.product.entity;

import com.inmed.company.entity.Company;
import com.inmed.category.entity.Category;
import com.inmed.supplier.entity.Supplier;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name="products",
        indexes = {
                @Index(
                        name="idx_product_code",
                        columnList="code"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable=false,
            unique=true,
            length=50
    )
    private String code;

    @Column(
            nullable=false,
            length=150
    )
    private String name;

    @Column(
            length=255
    )
    private String shortDescription;

    @Column(
            columnDefinition="TEXT"
    )
    private String longDescription;

    @Column(
            precision=10,
            scale=2
    )
    private BigDecimal cost;

    @Column(
            precision=10,
            scale=2
    )
    private BigDecimal salePrice;

    @Column(
            precision=10,
            scale=2
    )
    private BigDecimal wholesalePrice;

    private Integer relativeGain;

    private Boolean active;

    private Integer syncVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="category_id",
            nullable=false
    )
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="supplier_id",
            nullable=false
    )
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="company_id",
            nullable=false
    )
    private Company company;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
        if(active==null){
            active=true;
        }
        if(syncVersion==null){
            syncVersion=1;
        }
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt=LocalDateTime.now();
        syncVersion++;
    }
}