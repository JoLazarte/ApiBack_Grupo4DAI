package com.uade.tpo.api_grupo4.entity;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*; // Importa todo de jakarta.persistence
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "material_used") // Es buena práctica ser explícito con el nombre de la tabla
public class MaterialUsed implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELACIONES CORREGIDAS ---

    // 1. Relación con Recipe: Muchos "MaterialUsed" pertenecen a UNA "Recipe".
    // Esto creará una columna "recipe_id" en esta tabla.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonBackReference("recipe-materials") // <-- Añade el mismo nombre aquí
    private Recipe recipe;

    // 2. Relación con Ingredient: Muchos "MaterialUsed" pueden usar EL MISMO "Ingredient".
    // (Ej: "200gr Harina" y "300gr Harina" apuntan al mismo ingrediente "Harina")
    // Esto creará una columna "ingredient_id".
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    // 3. Relación con Unit: Muchos "MaterialUsed" pueden usar LA MISMA "Unit".
    // (Ej: "200gr Harina" y "50gr Azúcar" apuntan a la misma unidad "gramos")
    // Esto creará una columna "unity_id".
    @ManyToOne
    @JoinColumn(name = "unity_id") // Puede ser nullable si a veces no se especifica unidad
    private Unit unity;


    // --- CAMPOS PROPIOS ---

    @Column(nullable = false)
    private int quantity;

    // Este campo estaba en tu BD pero faltaba en la entidad
    private String observation;
}