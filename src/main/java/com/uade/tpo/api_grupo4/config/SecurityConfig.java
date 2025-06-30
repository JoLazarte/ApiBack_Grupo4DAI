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

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Rutas públicas para registro y login
                        .requestMatchers(
                                "/apiUser/loginUser",
                                "/apiUser/registerUser",
                                "/apiUser/check-alias",
                                "/apiUser/check-email",
                                "/apiUser/iniciar-registro",
                                "/apiUser/finalizar-registro"
                        ).permitAll()
                        
                        // 2. Rutas públicas para cargar datos maestros (types, units)
                        .requestMatchers(HttpMethod.POST, "/apiRecipes/types").permitAll()
                        .requestMatchers(HttpMethod.POST, "/apiRecipes/units").permitAll()

                        // ▼▼▼ ¡LA NUEVA LÍNEA QUE AÑADIMOS! ▼▼▼
                        // 3. Permite que cualquiera pueda VER (GET) la lista de recetas y sus búsquedas.
                        .requestMatchers(HttpMethod.GET, "/apiRecipes", "/apiRecipes/search/**", "/apiRecipes/types", "/apiRecipes/units").permitAll()

                        // 4. Para cualquier otra petición (como POST /apiRecipes), se requerirá autenticación.
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}