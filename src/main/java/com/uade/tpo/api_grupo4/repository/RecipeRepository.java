package com.uade.tpo.api_grupo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.uade.tpo.api_grupo4.entity.Recipe;
import java.util.Optional;



@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /**
     * Busca recetas cuyo nombre contenga el texto proporcionado, ignorando mayúsculas/minúsculas.
     * @param name El texto a buscar dentro del nombre de la receta.
     * @param sort El objeto Sort para definir el orden de los resultados.
     * @return Una lista de recetas que coinciden con la búsqueda.
     */
    Page<Recipe> findByRecipeNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Recipe> findByUserUsernameContainingIgnoreCase(String username, Pageable pageable);
    /**
     * Busca recetas que pertenezcan a un tipo de receta específico, por su ID.
     * Devuelve los resultados paginados.
     * @param typeId El ID del TypeOfRecipe por el cual filtrar.
     * @param pageable El objeto Pageable que contiene la info de paginación y orden.
     * @return Una Página de recetas que coinciden con el tipo.
     */
    Page<Recipe> findByTypeOfRecipeId(Long typeId, Pageable pageable);
    /**
     * Busca recetas que SÍ CONTIENEN un ingrediente cuyo nombre coincida parcialmente.
     * Usa LIKE para buscar el término dentro del nombre del ingrediente.
     */
    @Query("SELECT r FROM Recipe r JOIN r.ingredients m JOIN m.ingredient i WHERE lower(i.name) LIKE lower(concat('%', :ingredientName, '%'))")
    Page<Recipe> findRecipesWithIngredient(@Param("ingredientName") String ingredientName, Pageable pageable);

    /**
     * Busca recetas que NO CONTIENEN un ingrediente cuyo nombre coincida parcialmente.
     */
    @Query("SELECT r FROM Recipe r WHERE r.id NOT IN (SELECT r2.id FROM Recipe r2 JOIN r2.ingredients m JOIN m.ingredient i WHERE lower(i.name) LIKE lower(concat('%', :ingredientName, '%')))")
    Page<Recipe> findRecipesWithoutIngredient(@Param("ingredientName") String ingredientName, Pageable pageable);

    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.reviews rev LEFT JOIN FETCH rev.user WHERE r.id = :id")
    Optional<Recipe> findByIdWithReviewsAndAuthor(@Param("id") Long id);
}

