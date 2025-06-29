package com.uade.tpo.api_grupo4.controllers.recipe;

import lombok.Data;

@Data
public class MaterialRequestDTO {
    // Soportaremos ambos. El frontend puede enviar uno u otro.
    private Long ingredientId;
    private String ingredientName;
    
    private Long unitId;
    private int quantity;
    private String observation;
}