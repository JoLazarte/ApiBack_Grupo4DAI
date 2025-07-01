package com.uade.tpo.api_grupo4.repository;

import com.uade.tpo.api_grupo4.entity.Person;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.SavedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, Long> {
    Optional<SavedRecipe> findByUserAndRecipe(Person user, Recipe recipe);
    List<SavedRecipe> findByUser(Person user);
    boolean existsByUserAndRecipe(Person user, Recipe recipe);
}