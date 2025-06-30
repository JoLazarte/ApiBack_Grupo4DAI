package com.uade.tpo.api_grupo4.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference
    private Person user;
    private String recipeName;
    @Column(columnDefinition = "LONGTEXT")
    private String descriptionGeneral;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Step> description;	
    @OneToMany(
    mappedBy = "recipe",
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    @JsonManagedReference("recipe-materials")
    private List<MaterialUsed> ingredients;
    @Column(columnDefinition = "LONGTEXT")
    private String mainPicture;
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