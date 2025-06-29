package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * Busca un ingrediente por su nombre. Spring Data JPA implementará este método
     * automáticamente basándose en su nombre.
     * @param name El nombre del ingrediente a buscar.
     * @return Un Optional que contendrá el ingrediente si se encuentra, o estará vacío si no.
     */
    Optional<Ingredient> findByName(String name);
}