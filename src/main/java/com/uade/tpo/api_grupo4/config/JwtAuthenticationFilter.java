package com.uade.tpo.api_grupo4.config;

import com.uade.tpo.api_grupo4.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("--- [DEBUG] Iniciando JwtAuthenticationFilter para la ruta: " + request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("--- [DEBUG] No se encontró cabecera 'Authorization' con 'Bearer'. Pasando al siguiente filtro.");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        System.out.println("--- [DEBUG] Token extraído: " + jwt);

        try {
            username = jwtService.extractUsername(jwt);
            System.out.println("--- [DEBUG] Username extraído del token: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("--- [DEBUG] Usuario no autenticado. Cargando detalles para: " + username);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("--- [DEBUG] Usuario encontrado en la BD: " + userDetails.getUsername());

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("--- [DEBUG] El token es VÁLIDO. Autenticando al usuario.");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("--- [DEBUG] El token NO es válido.");
                }
            } else {
                System.out.println("--- [DEBUG] El username es nulo o el usuario ya está autenticado.");
            }
        } catch (Exception e) {
            System.out.println("--- [DEBUG] Ocurrió una excepción al procesar el token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
