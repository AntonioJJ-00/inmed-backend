package com.inmed.branch.mapper;


import com.inmed.branch.dto.response.BranchResponse;
import com.inmed.branch.entity.Branch;


public class BranchMapper {


    private BranchMapper(){}



    public static BranchResponse toResponse(
            Branch branch
    ){

        return BranchResponse.builder()

                .id(branch.getId())

                .name(branch.getName())

                .code(branch.getCode())

                .address(branch.getAddress())

                .phone(branch.getPhone())

                .status(branch.getStatus())

                .companyId(
                        branch.getCompany().getId()
                )

                .createdAt(branch.getCreatedAt())

                .updatedAt(branch.getUpdatedAt())

                .build();

    }


}