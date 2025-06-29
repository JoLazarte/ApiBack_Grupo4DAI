package com.uade.tpo.api_grupo4.controllers.recipe;

import lombok.Data;

@Data
public class CreateUnitRequest {
    // Usamos 'description' para que coincida con el campo de tu entidad Unit.java
    private String description;
}