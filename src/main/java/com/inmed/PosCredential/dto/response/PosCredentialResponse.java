package com.inmed.PosCredential.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PosCredentialResponse {

    private Long id;

    private Long posId;

    private String username;

    private Boolean enabled;
}