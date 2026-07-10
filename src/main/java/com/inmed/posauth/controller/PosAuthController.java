package com.inmed.posauth.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import com.inmed.posauth.dto.request.PosLoginRequest;
import com.inmed.posauth.dto.response.PosLoginResponse;
import com.inmed.posauth.facade.PosAuthFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pos/auth")
@RequiredArgsConstructor
public class PosAuthController {

    private final PosAuthFacade posAuthFacade;

    @PostMapping("/login")
    public ApiResponse<PosLoginResponse> login(
            @Valid
            @RequestBody PosLoginRequest request
    ){
        return ResponseFactory.success(
                "POS login successfully",
                posAuthFacade.login(request)
        );
    }
}