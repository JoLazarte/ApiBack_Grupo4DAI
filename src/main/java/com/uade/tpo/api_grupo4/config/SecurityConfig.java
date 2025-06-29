package com.uade.tpo.api_grupo4.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Inyectamos el nuevo filtro y el proveedor de autenticación
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Definimos las rutas públicas
                        .requestMatchers(
                                "/apiUser/loginUser",
                                "/apiUser/registerUser",
                                "/apiUser/check-alias",
                                "/apiUser/check-email",
                                "/apiUser/iniciar-registro",
                                "/apiUser/finalizar-registro"
                        ).permitAll()
                        // Para cualquier otra petición, se requerirá autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                // ▼▼▼ ¡LA LÍNEA MÁS IMPORTANTE! ▼▼▼
                // Añadimos nuestro filtro JWT ANTES del filtro de usuario/contraseña de Spring.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
