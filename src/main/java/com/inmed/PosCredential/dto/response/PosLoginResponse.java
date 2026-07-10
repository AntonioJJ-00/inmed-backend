package com.inmed.PosCredential.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PosLoginResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private Long posId;

    private Long branchId;

    private String branchName;

    private String posCode;
}