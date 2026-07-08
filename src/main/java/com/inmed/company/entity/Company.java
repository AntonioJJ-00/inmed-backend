package com.inmed.company.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "companies"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
            nullable = false,
            length = 150
    )
    private String businessName;


    @Column(
            nullable = false,
            length = 100
    )
    private String tradeName;


    @Column(
            nullable = false,
            unique = true,
            length = 20
    )
    private String taxId;


    @Column(
            length = 150
    )
    private String email;


    @Column(
            length = 30
    )
    private String phone;


    @Column(
            length = 255
    )
    private String website;


    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private CompanyStatus status;


    @Column(
            nullable = false,
            updatable = false
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

    }



    @PreUpdate
    public void onUpdate(){

        updatedAt = LocalDateTime.now();

    }

}