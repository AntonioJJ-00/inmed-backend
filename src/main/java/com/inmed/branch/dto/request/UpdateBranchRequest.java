package com.inmed.branch.dto.request;


import com.inmed.branch.entity.BranchStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateBranchRequest {


    private String name;


    private String address;


    private String phone;


    private BranchStatus status;


}