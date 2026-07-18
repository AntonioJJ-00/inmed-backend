package com.inmed.category.dto.request;

import com.inmed.category.entity.CategoryStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    private CategoryStatus status;

}