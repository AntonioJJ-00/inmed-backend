package com.inmed.branch.dto.response;


import com.inmed.branch.entity.BranchStatus;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;



@Getter
@Builder
public class BranchResponse {


    private Long id;


    private String name;


    private String code;


    private String address;


    private String phone;


    private BranchStatus status;


    private Long companyId;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

}