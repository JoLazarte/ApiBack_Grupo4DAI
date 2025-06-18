package com.uade.tpo.api_grupo4.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.user.UserView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Person persona;

    public UserView toView() {
        return new UserView(
                this.id,
                this.savedRecipes,
                this.recipes,
                this.reviews
                
            );
    }
    public UserView toViewDos(){
        return new UserView(
            this.id,
            this.persona.getUsername(), 
            this.persona.getFirstName(), 
            this.persona.getLastName(), 
            this.persona.getEmail(), 
            this.persona.getPassword(), 
            this.persona.getPhone(), 
            this.persona.getAddress(), 
            this.persona.getUrlAvatar(), 
            this.persona.getPermissionGranted(), 
            this.savedRecipes, 
            this.recipes, 
            this.reviews
            );
    }


    
}
