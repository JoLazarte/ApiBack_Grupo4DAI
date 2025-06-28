package com.uade.tpo.api_grupo4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PendingUser {
    @Id
    private String email;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String urlAvatar;
    private Boolean permissionGranted;

    // Campos de estudiante
    private String cardNumber;
    private String dniFrente;
    private String dniDorso;
    private String nroTramite;
    private String nroDocumento;
    private String tipoTarjeta;

    // Campos para la verificación
    private String verificationCode;
    private LocalDateTime expiryDate;
}