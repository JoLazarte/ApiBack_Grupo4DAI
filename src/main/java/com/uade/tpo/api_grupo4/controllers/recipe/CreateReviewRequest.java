package com.uade.tpo.api_grupo4.controllers.recipe;

import lombok.Data;

@Data
public class CreateReviewRequest {
    private int rating; // La calificación (ej: 1 a 5 estrellas)
    private String comment; // El comentario de la reseña
}