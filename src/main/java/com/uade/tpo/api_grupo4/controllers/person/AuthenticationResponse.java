// Archivo: src/main/java/com/uade/tpo/api_grupo4/controllers/person/AuthenticationResponse.java
package com.uade.tpo.api_grupo4.controllers.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    // Esta clase solo necesita un campo para contener el token que devolveremos
    private String token;
}