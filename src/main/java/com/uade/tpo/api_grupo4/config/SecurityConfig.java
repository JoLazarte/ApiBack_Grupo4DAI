// Archivo: src/main/java/com/uade/tpo/api_grupo4/config/SecurityConfig.java

package com.uade.tpo.api_grupo4.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF
                .authorizeHttpRequests(auth -> auth
                        // Definimos aquí las rutas que serán públicas para todos
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
                // Le decimos a Spring que no cree sesiones, usaremos JWT (sin estado)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider);

        // En un futuro paso, aquí insertaremos nuestro filtro de JWT
        // http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}