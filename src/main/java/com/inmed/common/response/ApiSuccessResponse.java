package com.inmed.common.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiSuccessResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private LocalDateTime timestamp;

}