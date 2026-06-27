package com.inmed.security.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getRequestURI().equals("/api/auth/login")
                && HttpMethod.POST.matches(request.getMethod())) {

            String clientIp = request.getRemoteAddr();

            boolean allowed =
                    rateLimiterService.allowRequest(clientIp);

            if (!allowed) {

                response.setStatus(
                        HttpStatus.TOO_MANY_REQUESTS.value()
                );

                response.setContentType("application/json");

                response.getWriter().write("""
                        {
                            "status":429,
                            "message":"Too many login attempts. Please try again later."
                        }
                        """);

                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}