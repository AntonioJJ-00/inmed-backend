package com.inmed.PosCredential.mapper;

import com.inmed.PosCredential.dto.response.PosCredentialResponse;
import com.inmed.PosCredential.entity.PosCredential;

public class PosCredentialMapper {

    private PosCredentialMapper() {
    }

    public static PosCredentialResponse toResponse(
            PosCredential credential
    ){
        if(credential == null){
            return null;
        }
        return PosCredentialResponse.builder()

                .id(
                        credential.getId()
                )

                .posId(
                        credential.getPosDevice().getId()
                )

                .username(
                        credential.getUsername()
                )

                .enabled(
                        credential.getEnabled()
                )

                .build();
    }
}