package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.controllers.person.PersonView;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Review;
import com.uade.tpo.api_grupo4.entity.SavedRecipe;
import com.uade.tpo.api_grupo4.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class UserView extends PersonView {

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
    
    public UserView(Long id, String username, String firstName, String lastName, String email, String password,
            String phone, String address, String urlAvatar, Boolean permissionGranted,
            List<SavedRecipe> savedRecipes,List<Recipe> recipes, List<Review> reviews) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.urlAvatar = urlAvatar;
        this.permissionGranted = permissionGranted;
        this.savedRecipes = savedRecipes;
        this.recipes = recipes;
        this.reviews = reviews;
    }

     public User toEntity(){
        return new User(
            this.id,
            this.username, 
            this.firstName, 
            this.lastName, 
            this.email, 
            this.password, 
            this.phone, 
            this.address, 
            this.urlAvatar, 
            this.permissionGranted, 
            this.savedRecipes, 
            this.recipes, 
            this.reviews
            );
    }

    

   
}
