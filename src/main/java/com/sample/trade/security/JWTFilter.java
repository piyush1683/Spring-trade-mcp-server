package com.sample.trade.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTTokenGenService jwtTokenGenService;

    public JWTFilter(JWTTokenGenService jwtTokenGenService) {
        this.jwtTokenGenService = jwtTokenGenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, java.io.IOException {
        // Implement JWT validation logic here
        System.out.println("JWT Filter is processing the request");
        String authheader = request.getHeader("Authorization");
        String jwtToken = null;
        if (authheader != null && !authheader.startsWith("Bearer ")) {
            jwtToken = authheader.substring(7);
            System.out.println("JWT Token: " + jwtToken);
        } else {
            System.out.println("No JWT Token found in the request");
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("No authentication found in the security context");
            if (jwtTokenGenService.validateToken(jwtToken)) {
                System.out.println("JWT Token is valid");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        "piyush",
                        "admin", null);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("JWT Token is invalid");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }
        } else {
            System.out.println(
                    "Authentication found: " + SecurityContextHolder.getContext().getAuthentication().getName());
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        // Logic to determine if the filter should not be applied
        String path = request.getServletPath();
        if (path.startsWith("/login") || path.startsWith("/index.html") || path.startsWith("/mcp-client.html")
                || path.startsWith("/mcp/") || path.startsWith("/topics/") || path.startsWith("/app/")
                || path.startsWith("/endpoints") || path.startsWith("/health") || path.startsWith("/actuator")
                || path.startsWith("/ai/") || path.startsWith("/ai/**") || path.startsWith("/actuator/**")
                || path.startsWith("/public/") || path.startsWith("/static/") || path.startsWith("/resources/")
                || path.startsWith("/webjars/")) {
            return true; // Skip the filter for public API paths
        }
        // return false; // Apply the filter for all requests
        return false;
    }
}
