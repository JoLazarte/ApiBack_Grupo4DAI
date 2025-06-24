package com.uade.tpo.api_grupo4.controllers.person;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Boolean permissionGranted;

    // --- NUEVOS CAMPOS AÑADIDOS ---
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String urlAvatar;
    // --- FIN DE NUEVOS CAMPOS ---

    // Campos existentes para Estudiante
    private String cardNumber;
    private String dniFrente;
    private String dniDorso;
    private String nroTramite;
    private String nroDocumento;
    private String tipoTarjeta;
}