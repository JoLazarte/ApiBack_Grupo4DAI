package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.TypeOfRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TypeOfRecipeRepository extends JpaRepository<TypeOfRecipe, Long> {
    
    /**
     * Busca un tipo de receta por su nombre, ignorando mayúsculas y minúsculas.
     * Ej: buscar "postres" encontrará "Postres".
     * @param name El nombre a buscar.
     * @return Un Optional que contiene el TypeOfRecipe si se encuentra.
     */
    Optional<TypeOfRecipe> findByNameIgnoreCase(String name);
}