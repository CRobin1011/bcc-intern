package com.ignit.internship.config;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ignit.internship.model.User;
import com.ignit.internship.service.JwtTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = auth.substring(7);

            Claims parsedToken = jwtTokenService.parseToken(token);

            if (parsedToken.getExpiration().after(Date.from(jwtTokenService.getCurrentDate().toInstant(ZoneOffset.UTC)))) {
                throw new ExpiredJwtException(null, parsedToken, "Token expired");
            }

            Long id = Long.parseLong(parsedToken.getId());
            String username = parsedToken.getSubject();
            String email = parsedToken.get("email", String.class);
            User user = (User) userDetailsService.loadUserByUsername(username);
            if (!user.getId().equals(id) || 
                !user.getUsername().equals(username) ||
                !user.getEmail().equals(email)
            ) {
                throw new Exception("Token invalid");
            }

            SecurityContext context = SecurityContextHolder.getContext();
            if (context.getAuthentication() == null) {
                context.setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
                );
            }

            filterChain.doFilter(request, response);
            
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
    }

}
