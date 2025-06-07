package com.uade.tpo.api_grupo4.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.user.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String address;
    @Column
    private Role role;
    //@NotEmpty
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String urlAvatar;
    @Column
    private int userStatus;	
    @Column
    private Boolean permissionGranted;
    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SavedRecipe> savedRecipes;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    @JsonManagedReference
    private Student student;
    
    public UserDTO toDTO() {
        return new UserDTO(
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
                this.student
              
                );
    }

    public void assignStudent(Student student) {
        this.student.setUser(this);
    }

    public void updateData(User newUser){
        setFirstName(newUser.getFirstName());
        setLastName(newUser.getLastName());
        setEmail(newUser.getEmail());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

}
