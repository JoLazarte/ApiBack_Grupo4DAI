package com.uade.tpo.api_grupo4.controllers.recipe;

// --- Importaciones de Clases del Proyecto ---
import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.entity.Ingredient;
import com.uade.tpo.api_grupo4.entity.MaterialUsed;
import com.uade.tpo.api_grupo4.entity.Person;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.entity.TypeOfRecipe;
import com.uade.tpo.api_grupo4.entity.Unit;


// --- Importaciones de DTOs (CORREGIDAS) ---
import com.uade.tpo.api_grupo4.controllers.recipe.MaterialRequestDTO;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateRecipeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateTypeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateUnitRequest;



// --- Importaciones de Spring y Lombok ---
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiRecipes")
@RequiredArgsConstructor // Usamos esto para una inyección limpia
public class ApiRecipe {

    // Inyectamos tu clase Controlador, que actúa como nuestro servicio
    private final Controlador controlador;


    // ENDPOINT PARA CREAR UNA RECETA COMPLETA (CON MATERIALES Y PASOS)
    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeRequest request, Authentication authentication) {
        try {
            // Obtenemos el principal, que ahora sabemos que es de tipo Person (o una subclase como User o Student)
            Person author = (Person) authentication.getPrincipal();
            
            // Llamamos al servicio pasando el autor de tipo Person
            Recipe createdRecipe = controlador.createRecipeWithMaterials(request, author);
            return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para crear un Ingrediente suelto
    @PostMapping("/ingredients")
    public ResponseEntity<?> addIngredient(@RequestBody Ingredient ingredient) {
        try {
            Ingredient newIngredient = controlador.createIngredient(ingredient);
            return new ResponseEntity<>(newIngredient, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error al procesar la solicitud: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para añadir un Material a una Receta que YA EXISTE
    @PostMapping("/{recipeId}/materials")
    public ResponseEntity<?> addMaterialToRecipeEndpoint(@PathVariable Long recipeId, @RequestBody MaterialRequestDTO request) { // <-- CORREGIDO
        try {
            MaterialUsed createdMaterial = controlador.addMaterialToRecipe(recipeId, request);
            return new ResponseEntity<>(createdMaterial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/types")
    public ResponseEntity<?> createRecipeType(@RequestBody CreateTypeRequest request) {
        try {
            TypeOfRecipe newType = controlador.createTypeOfRecipe(request);
            return new ResponseEntity<>(newType, HttpStatus.CREATED);
        } catch (Exception e) {
            // Si el servicio lanza la excepción de "ya existe", la atrapamos aquí
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/units")
    public ResponseEntity<?> createUnit(@RequestBody CreateUnitRequest request) {
        try {
            Unit newUnit = controlador.createUnit(request);
            return new ResponseEntity<>(newUnit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}