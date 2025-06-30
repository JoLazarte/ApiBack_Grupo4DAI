package com.uade.tpo.api_grupo4.controllers.user;

import lombok.Data;

// Esta clase contiene los campos adicionales necesarios para ser un estudiante.
@Data
public class BecomeStudentRequest {
    private String cardNumber;
    private String dniFrente;
    private String dniDorso;
    private String nroTramite;
    private String nroDocumento;
    private String tipoTarjeta;
}
