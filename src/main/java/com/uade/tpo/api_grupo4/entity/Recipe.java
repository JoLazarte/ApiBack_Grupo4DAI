package com.uade.tpo.api_grupo4.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
//import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference
    private User user;
    private String recipeName;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Step> description;	
    @ManyToMany
    @JoinTable(
    name = "materialUsed_recipe", 
    joinColumns = @JoinColumn(name = "recipe_id"), 
    inverseJoinColumns = @JoinColumn(name = "materialUsed_id"))
    private List<MaterialUsed> ingredients;       	
    //@NotEmpty
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    @JsonBackReference 
    private Picture mainPicture;
    private int servings;	
    private int comensales;	
    @ManyToOne
    @JoinColumn(name = "typeOfRecipe_id")
    private TypeOfRecipe typeOfRecipe; //seria como la categoria. Ej: vegana, desayuno, etc
    @NotNull
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;
    

}
