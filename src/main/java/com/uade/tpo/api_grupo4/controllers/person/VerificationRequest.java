// Archivo: src/main/java/com/uade/tpo/api_grupo4/controllers/person/VerificationRequest.java
package com.uade.tpo.api_grupo4.controllers.person;

import lombok.Data;

@Data
public class VerificationRequest {
    private String email;
    private String code;
}