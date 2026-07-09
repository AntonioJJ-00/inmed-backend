package com.inmed.pos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePosRequest {

    @NotNull
    private Long branchId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    private String serialNumber;

    @NotBlank
    private String appVersion;

}