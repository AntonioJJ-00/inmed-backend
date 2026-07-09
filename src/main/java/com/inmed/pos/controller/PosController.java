package com.inmed.pos.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import com.inmed.pos.dto.request.CreatePosRequest;
import com.inmed.pos.dto.request.UpdatePosRequest;
import com.inmed.pos.dto.response.PosResponse;
import com.inmed.pos.facade.PosFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pos")
@RequiredArgsConstructor
public class PosController {

    private final PosFacade posFacade;
    /**
     * Crear POS en una sucursal
     */
    @PostMapping
    public ApiResponse<PosResponse> create(
            @Valid
            @RequestBody CreatePosRequest request
    ){
        return ResponseFactory.success(
                "POS created successfully",
                posFacade.createPos(request)
        );
    }
    /**
     * Obtener todos los POS
     */
    @GetMapping
    public ApiResponse<List<PosResponse>> getAll(){
        return ResponseFactory.success(
                "POS retrieved successfully",
                posFacade.getAll()
        );
    }
    /**
     * Obtener POS por ID
     */
    @GetMapping("/{id}")
    public ApiResponse<PosResponse> getById(
            @PathVariable Long id
    ){
        return ResponseFactory.success(
                "POS retrieved successfully",
                posFacade.getById(id)
        );
    }
    /**
     * Obtener POS pertenecientes a una sucursal
     */
    @GetMapping("/branch/{branchId}")
    public ApiResponse<List<PosResponse>> getByBranch(
            @PathVariable Long branchId
    ){
        return ResponseFactory.success(
                "Branch POS retrieved successfully",
                posFacade.getByBranch(branchId)
        );
    }
    /**
     * Actualizar POS
     */
    @PutMapping("/{id}")
    public ApiResponse<PosResponse> update(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdatePosRequest request
    ){
        return ResponseFactory.success(
                "POS updated successfully",
                posFacade.update(id, request)
        );
    }
    /**
     * Deshabilitar POS
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Long id
    ){
        posFacade.delete(id);
        return ResponseFactory.success(
                "POS disabled successfully"
        );
    }
}