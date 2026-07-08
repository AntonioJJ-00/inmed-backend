package com.inmed.company.dto.response;

import com.inmed.company.entity.CompanyStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CompanyResponse {

    private Long id;

    private String businessName;

    private String tradeName;

    private String taxId;

    private String email;

    private String phone;

    private String website;

    private CompanyStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}