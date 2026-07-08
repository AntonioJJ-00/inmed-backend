package com.inmed.branch.entity;


import com.inmed.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name="branches",
        indexes = {
                @Index(
                        name="idx_branch_code",
                        columnList="code"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
            nullable=false,
            length=100
    )
    private String name;


    @Column(
            nullable=false,
            unique=true,
            length=50
    )
    private String code;


    @Column(
            length=255
    )
    private String address;


    private String phone;



    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private BranchStatus status;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="company_id",
            nullable=false
    )
    private Company company;



    @Column(nullable=false)
    private LocalDateTime createdAt;



    @Column(nullable=false)
    private LocalDateTime updatedAt;



    @PrePersist
    public void create(){

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if(status==null){
            status=BranchStatus.ACTIVE;
        }
    }



    @PreUpdate
    public void update(){

        updatedAt=LocalDateTime.now();

    }

}