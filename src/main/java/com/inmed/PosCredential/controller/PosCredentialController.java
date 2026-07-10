package com.inmed.PosCredential.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;

import com.inmed.PosCredential.dto.request.CreatePosCredentialRequest;
import com.inmed.PosCredential.dto.response.PosCredentialResponse;
import com.inmed.PosCredential.facade.PosCredentialFacade;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pos/credentials")
@RequiredArgsConstructor
public class PosCredentialController {

    private final PosCredentialFacade credentialFacade;
    /**
     * Crear credencial para un POS
     */
    @PostMapping
    public ApiResponse<PosCredentialResponse> create(
            @Valid
            @RequestBody CreatePosCredentialRequest request
    ){
        return ResponseFactory.success(
                "POS credential created successfully",
                credentialFacade.create(request)
        );
    }
    /**
     * Obtener todas las credenciales
     */
    @GetMapping
    public ApiResponse<List<PosCredentialResponse>> getAll(){
        return ResponseFactory.success(
                "POS credentials retrieved successfully",
                credentialFacade.getAll()
        );
    }
    /**
     * Obtener credencial por ID
     */
    @GetMapping("/{id}")
    public ApiResponse<PosCredentialResponse> getById(
            @PathVariable Long id
    ){
        return ResponseFactory.success(
                "POS credential retrieved successfully",
                credentialFacade.getById(id)
        );
    }
    /**
     * Deshabilitar credencial
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> disable(
            @PathVariable Long id
    ){
        credentialFacade.disable(id);
        return ResponseFactory.success(
                "POS credential disabled successfully"
        );
    }
}