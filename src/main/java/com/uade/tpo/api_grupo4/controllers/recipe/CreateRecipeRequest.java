package com.uade.tpo.api_grupo4.controllers.recipe;

import lombok.Data;
import java.util.List;

@Data
public class CreateRecipeRequest {
    private String recipeName;
    private String mainPicture;
    private String descriptionGeneral;
    private int servings;
    private int cantidadPersonas;
    private Long typeOfRecipeId;
    private List<StepRequestDTO> steps;
    private List<MaterialRequestDTO> ingredients;
}