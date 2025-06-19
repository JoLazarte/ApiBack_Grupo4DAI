package com.uade.tpo.api_grupo4.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.user.UserView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends Person {
   
    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SavedRecipe> savedRecipes;
    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Recipe> recipes;
    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;

    public User(Long id, String username, String firstName, String lastName, String email, String password,
            String phone, String address, String urlAvatar, Boolean permissionGranted
            ,List<SavedRecipe> savedRecipes,List<Recipe> recipes, List<Review> reviews
            ) {
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
    

    public UserView toView(){
        return new UserView(
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
