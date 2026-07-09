package com.inmed.pos.dto.request;

import com.inmed.pos.entity.PosStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePosRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    private String appVersion;

    @NotNull
    private PosStatus status;

    @NotNull
    private Boolean enabled;

}