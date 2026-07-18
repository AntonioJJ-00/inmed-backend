package com.inmed.category.dto.response;

import com.inmed.category.entity.CategoryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CategoryResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private CategoryStatus status;
    private Long companyId;
    private Long syncVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}