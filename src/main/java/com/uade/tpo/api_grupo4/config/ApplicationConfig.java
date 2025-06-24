// Archivo: src/main/java/com/uade/tpo/api_grupo4/config/ApplicationConfig.java

package com.uade.tpo.api_grupo4.config;

import com.uade.tpo.api_grupo4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        // Le decimos a Spring Security que para buscar un usuario por su 'username',
        // debe usar el método findByUsername de nuestro UserRepository.
        return username -> userRepository.findByUsername(username);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Este es el "proveedor de datos" que usará Spring Security.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        // Aquí le indicamos el "comparador" de contraseñas.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ⚠️ ¡Inseguro! Le decimos a Spring que no espere contraseñas cifradas.
        // Las tratará como texto plano. Solo para este proyecto.
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Exponemos el AuthenticationManager de Spring para poder usarlo en nuestro Controlador.
        return config.getAuthenticationManager();
    }
}