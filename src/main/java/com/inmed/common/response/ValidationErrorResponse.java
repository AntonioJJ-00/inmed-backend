package com.inmed.common.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ValidationErrorResponse {

    private Integer status;

    private String message;

    private List<String> errors;

    private LocalDateTime timestamp;

}