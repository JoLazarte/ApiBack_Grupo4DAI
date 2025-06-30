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
import com.uade.tpo.api_grupo4.entity.Review;


// --- Importaciones de DTOs (CORREGIDAS) ---
import com.uade.tpo.api_grupo4.controllers.recipe.MaterialRequestDTO;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateRecipeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateTypeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateUnitRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateReviewRequest;



// --- Importaciones de Spring y Lombok ---
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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

    @PostMapping("/{recipeId}/reviews")
    public ResponseEntity<?> createReview(
            @PathVariable Long recipeId,
            @RequestBody CreateReviewRequest request,
            Authentication authentication) {

        try {
            // Obtenemos la Persona (User o Student) que está haciendo la reseña
            Person author = (Person) authentication.getPrincipal();

            // Llamamos al servicio para crear la reseña
            Review createdReview = controlador.createReview(recipeId, request, author);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRecipes(
            @RequestParam(defaultValue = "alpha_asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) { // <-- Nuevos parámetros
        
        try {
            // La respuesta del servicio ahora es un objeto Page
            Page<Recipe> recipePage = controlador.findAllRecipes(sort, page, size);
            return new ResponseEntity<>(recipePage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/search/by-name")
    public ResponseEntity<Page<Recipe>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "alpha_asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Recipe> recipes = controlador.findRecipesByName(name, sort, page, size);
        return ResponseEntity.ok(recipes); // Siempre devuelve 200 OK
    }


    @GetMapping("/search/by-author")
    public ResponseEntity<Page<Recipe>> searchByAuthor(
            @RequestParam String username,
            @RequestParam(defaultValue = "alpha_asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Recipe> recipes = controlador.findRecipesByAuthor(username, sort, page, size);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/types")
    public ResponseEntity<?> getAllRecipeTypes() {
        try {
            List<TypeOfRecipe> types = controlador.findAllTypes();
            return new ResponseEntity<>(types, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudieron obtener los tipos de receta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/units")
    public ResponseEntity<?> getAllUnits() {
        try {
            List<Unit> units = controlador.findAllUnits();
            return new ResponseEntity<>(units, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudieron obtener las unidades de medida.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/by-type")
    public ResponseEntity<Page<Recipe>> searchByType(
            @RequestParam Long typeId,
            @RequestParam(defaultValue = "alpha_asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Recipe> recipePage = controlador.findRecipesByType(typeId, sort, page, size);
        return ResponseEntity.ok(recipePage); // Siempre devolvemos 200 OK, incluso si la página está vacía
    }

    @GetMapping("/search/by-ingredient")
    public ResponseEntity<Page<Recipe>> searchByIngredient( // Cambiamos el tipo de retorno
            @RequestParam String name,
            @RequestParam(defaultValue = "true") boolean contains,
            @RequestParam(defaultValue = "alpha_asc") String sort,
            @RequestParam(defaultValue = "0") int page,     // <-- Asegúrate de que acepte 'page'
            @RequestParam(defaultValue = "10") int size) {  // <-- Asegúrate de que acepte 'size'
        
        // Llamamos al método actualizado del controlador, pasándole todos los parámetros
        Page<Recipe> recipePage = controlador.findRecipesByIngredientPresence(name, contains, sort, page, size);
        
        // Devolvemos la página completa con un 200 OK
        return ResponseEntity.ok(recipePage);
    }
}