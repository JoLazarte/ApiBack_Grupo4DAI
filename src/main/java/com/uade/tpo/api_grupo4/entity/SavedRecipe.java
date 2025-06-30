package com.uade.tpo.api_grupo4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*; // Importamos todo de jakarta.persistence
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedRecipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    // ▼▼▼ CAMBIO 1: La columna ahora apunta al ID de una Persona. ▼▼▼
    @JoinColumn(nullable = false, name = "person_id") 
    // Usamos un nombre único para la referencia JSON para evitar conflictos
    @JsonBackReference("saver-recipes")
    private Person user; // <-- CAMBIO 2: El tipo de campo ahora es Person.

    // ▼▼▼ CAMPO AÑADIDO: La receta que se guardó. Es fundamental. ▼▼▼
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "recipe_id")
    private Recipe recipe; 
}