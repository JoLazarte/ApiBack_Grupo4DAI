package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Review;
import com.uade.tpo.api_grupo4.entity.SavedRecipe;
import com.uade.tpo.api_grupo4.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {
    private Long id;
    private String username;
    private String firstName; 
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String urlAvatar;
    private Boolean permissionGranted;
    @JsonIgnore
    private List<SavedRecipe> savedRecipes;
    @JsonIgnore
    private List<Recipe> recipes;
    @JsonIgnore
    private List<Review> reviews;
    
    public UserView(Long id, List<SavedRecipe> savedRecipes, List<Recipe> recipes, List<Review> reviews ){
        this.id = id;
        this.savedRecipes = savedRecipes;
        this.recipes = recipes;
        this.reviews = reviews;
    }

   
}
