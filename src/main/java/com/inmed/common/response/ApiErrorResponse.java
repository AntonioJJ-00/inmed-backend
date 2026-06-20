package com.inmed.common.response;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
@Getter @Builder

public class ApiErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
}
