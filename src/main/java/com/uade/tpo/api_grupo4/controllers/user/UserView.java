// Archivo: UserView.java (Versión Corregida y Final)
package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;
import com.uade.tpo.api_grupo4.controllers.person.PersonView;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Review;
import com.uade.tpo.api_grupo4.entity.SavedRecipe;
import com.uade.tpo.api_grupo4.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true) // <-- Corregido para herencia
@NoArgsConstructor // Lombok usará el constructor de abajo
public class UserView extends PersonView {

    private List<SavedRecipe> savedRecipes;
    private List<Recipe> recipes;
    private List<Review> reviews;

    // ---> CONSTRUCTOR CORREGIDO USANDO SUPER() <---
    public UserView(Long id, String username, String firstName, String lastName, String email, String password,
                    String phone, String address, String urlAvatar, Boolean permissionGranted,
                    List<SavedRecipe> savedRecipes, List<Recipe> recipes, List<Review> reviews) {

        super(id, username, firstName, lastName, email, password, phone, address, urlAvatar, permissionGranted);

        this.savedRecipes = savedRecipes;
        this.recipes = recipes;
        this.reviews = reviews;
    }

    // ---> MÉTODO toEntity() CORREGIDO USANDO GETTERS <---
    public User toEntity() {
        return User.builder()
                .id(this.getId())
                .username(this.getUsername())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .email(this.getEmail())
                .password(this.getPassword())
                .phone(this.getPhone())
                .address(this.getAddress())
                .urlAvatar(this.getUrlAvatar())
                .permissionGranted(this.getPermissionGranted())
                .savedRecipes(this.savedRecipes)
                .recipes(this.recipes)
                .reviews(this.reviews)
                .build();
    }
}