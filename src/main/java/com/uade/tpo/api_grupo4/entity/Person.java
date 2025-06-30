package com.uade.tpo.api_grupo4.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(unique = true, nullable = false)
    protected String username;
    protected String firstName;
    protected String lastName;
    @Column(unique = true, nullable = false)
    protected String email;
    protected String password;
    @Column(unique = true)
    protected String phone;
    @Column(unique = true)
    protected String address;
    @Column(columnDefinition = "LONGTEXT")
    protected String urlAvatar;
    protected Boolean permissionGranted;

    // --- RELACIONES --

    // Lista de recetas CREADAS por esta Persona
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference("author-recipes")
    private List<Recipe> recipes;

    // Lista de recetas GUARDADAS por esta Persona
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference("saver-recipes")
    private List<SavedRecipe> savedRecipes;

    // Lista de reseñas ESCRITAS por esta Persona
    @OneToMany(mappedBy = "user")
    @JsonManagedReference("review-author")
    private List<Review> reviews;


    // --- Métodos de UserDetails ---
    @Override
    public abstract Collection<? extends GrantedAuthority> getAuthorities();
    
    // ... resto de tus métodos de UserDetails ...
}