package com.inmed.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID = "correlationId";
    public static final String HEADER_NAME = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String correlationId = request.getHeader(HEADER_NAME);

            if (correlationId == null || correlationId.isEmpty()) {
                correlationId = UUID.randomUUID().toString();
            }

            MDC.put(CORRELATION_ID, correlationId);

            response.setHeader(HEADER_NAME, correlationId);

            log.info("Request started: {} {}", request.getMethod(), request.getRequestURI());

            filterChain.doFilter(request, response);

        } finally {

            log.info("Request finished: {} {}", request.getMethod(), request.getRequestURI());

            MDC.clear();
        }
    }
}