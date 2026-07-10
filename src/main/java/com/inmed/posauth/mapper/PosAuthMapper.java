package com.inmed.posauth.mapper;

import com.inmed.posauth.dto.response.PosLoginResponse;
import com.inmed.PosCredential.entity.PosCredential;

public class PosAuthMapper {

    private PosAuthMapper(){
    }

    public static PosLoginResponse toLoginResponse(
            PosCredential credential,
            String token
    ){

        return PosLoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(3600L)
                .posId(
                        credential.getPosDevice().getId()
                )
                .branchId(
                        credential.getPosDevice()
                                .getBranch()
                                .getId()
                )
                .branchName(
                        credential.getPosDevice()
                                .getBranch()
                                .getName()
                )
                .posCode(
                        credential.getPosDevice()
                                .getCode()
                )
                .build();
    }
}