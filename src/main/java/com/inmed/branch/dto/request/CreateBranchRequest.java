package com.inmed.branch.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateBranchRequest {


    @NotBlank
    private String name;


    @NotBlank
    private String code;


    private String address;


    private String phone;

}