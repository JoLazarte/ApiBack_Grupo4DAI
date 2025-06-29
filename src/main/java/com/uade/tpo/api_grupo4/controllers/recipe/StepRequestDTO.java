package com.uade.tpo.api_grupo4.controllers.recipe;

import lombok.Data;

@Data
public class StepRequestDTO {
    private int numberOfStep; // Ajustado para coincidir con tu entidad
    private String comment;     // Ajustado para coincidir con tu entidad
    private String imagenPaso;  // Añadido
    private String videoPaso;   // Añadido
}