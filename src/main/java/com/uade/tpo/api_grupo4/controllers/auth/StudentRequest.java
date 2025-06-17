package com.uade.tpo.api_grupo4.controllers.auth;
//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private int cardNumber;

    private String dniFrente;
 
    private String dniDorso;

    private int nroTramite;

    private int cuentaCorriente;
    
}
