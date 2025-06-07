package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Review;
import com.uade.tpo.api_grupo4.entity.Role;
import com.uade.tpo.api_grupo4.entity.SavedRecipe;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;

import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String firstName; 
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String phone;
    @NotNull
    private String address;
    @NotNull
    private Role role;
    @NotNull
    private String urlAvatar;
    @NotNull
    private int userStatus;	
    @NotNull
    private Boolean permissionGranted;
    @JsonIgnore
    private List<SavedRecipe> savedRecipes;
    @JsonIgnore
    private List<Recipe> recipes;
    @JsonIgnore
    private List<Review> reviews;
    @JsonIgnore
    private Student student;
    
    public User toEntity() {
        return new User(
                this.id,
                this.username,
                this.firstName,
                this.lastName,
                this.email,
                this.password,
                this.phone,
                this.address,
                this.role,
                this.urlAvatar,
                this.userStatus,
                this.permissionGranted,
                this.savedRecipes,
                this.recipes,
                this.reviews,
                this.student
            );   
                
    }
}
