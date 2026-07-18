package com.inmed.sync.dto.request;

import com.inmed.sync.entity.SyncOperation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncOperationRequest {

    @NotBlank
    private String entity;

    @NotNull
    private SyncOperation operation;

    @NotBlank
    private String payload;
}