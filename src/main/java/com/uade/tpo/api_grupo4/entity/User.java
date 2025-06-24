// Archivo: src/main/java/com/uade/tpo/api_grupo4/entity/User.java

package com.uade.tpo.api_grupo4.entity;

import java.util.Collection; // <-- NUEVA IMPORTACIÓN
import java.util.List;

import org.springframework.security.core.GrantedAuthority; // <-- NUEVA IMPORTACIÓN
import org.springframework.security.core.authority.SimpleGrantedAuthority; // <-- NUEVA IMPORTACIÓN
import org.springframework.security.core.userdetails.UserDetails; // <-- NUEVA IMPORTACIÓN

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
// PASO 1: AÑADIMOS "implements UserDetails"
public class User extends Person implements UserDetails {

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
        // ... (tu método toView se mantiene igual)
        return new UserView(id, username, firstName, lastName, email, password, phone, address, urlAvatar, permissionGranted, savedRecipes, recipes, reviews);
    }

    // ====================================================================
    // PASO 2: AÑADIMOS LOS MÉTODOS REQUERIDOS POR LA INTERFAZ UserDetails
    // ====================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por ahora, le damos a todos los usuarios el rol "USER".
        // En el futuro, podrías tener roles como "ADMIN", etc.
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    // NOTA: Los siguientes dos métodos, getPassword() y getUsername(),
    // ya los provee automáticamente la anotación @Data de Lombok,
    // por lo que no necesitamos escribirlos explícitamente.

    @Override
    public boolean isAccountNonExpired() {
        // Devolvemos true para indicar que la cuenta nunca expira.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Devolvemos true para indicar que la cuenta nunca se bloquea.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Devolvemos true para indicar que la contraseña nunca expira.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Devolvemos true para indicar que el usuario está siempre habilitado.
        return true;
    }
}