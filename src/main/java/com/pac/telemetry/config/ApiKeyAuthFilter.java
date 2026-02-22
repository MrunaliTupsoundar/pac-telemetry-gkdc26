package com.pac.telemetry.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${app.device-api-token}")
    private String deviceApiToken;

    @Value("${app.admin-api-key}")
    private String adminApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/device/")) {
            String incomingToken = request.getHeader("X-Device-Token");
            if (incomingToken == null || !incomingToken.equals(deviceApiToken)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Invalid device token\"}");
                return;
            }
        }

        if (path.startsWith("/api/admin/")) {
            String incomingToken = request.getHeader("X-Admin-Key");
            if (incomingToken == null || !incomingToken.equals(adminApiKey)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Invalid admin key\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}