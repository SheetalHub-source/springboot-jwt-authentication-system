package com.example.JWTAuthSystem.Jwt;

import com.example.JWTAuthSystem.Configuration.CustomeUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomeUserDetailsService customUserDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomeUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

   /* @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractUsername(token);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }*/
  /*  @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

       final String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Extract JWT token from Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractUsername(token);
        }

        // Validate token and set authentication in SecurityContext
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)) {

                // Extract role from token
                String role = jwtUtil.extractRole(token);

                // Only check isActive for normal users
                if ("USER".equals(role)) {
                    Boolean isActive = jwtUtil.extractIsActive(token);
                    if (isActive == null || !isActive) {
                        // User not approved, block access
                        throw new ApiException("Your account is not approved yet. Contact admin.");
                    }
                }

                // Set authentication in SecurityContext
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }*/
   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain chain) throws ServletException, IOException {
       final String authHeader = request.getHeader("Authorization");
       String username = null;
       String jwt = null;

       if (authHeader != null && authHeader.startsWith("Bearer ")) {
           jwt = authHeader.substring(7);
           try {
               username = jwtUtil.extractUsername(jwt);
               Claims claims = jwtUtil.extractAllClaims(jwt);
               request.setAttribute("claims", claims);
           } catch (Exception e) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
               return;
           }
       }

       if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

           UsernamePasswordAuthenticationToken authToken =
                   new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

           SecurityContextHolder.getContext().setAuthentication(authToken);
       }

       chain.doFilter(request, response);
   }

}
