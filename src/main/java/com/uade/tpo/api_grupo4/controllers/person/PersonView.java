package com.uade.tpo.api_grupo4.controllers.person;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonView {
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
    private String urlAvatar;
    @NotNull
    private Boolean permissionGranted;
    
    @Override
    public String toString() {
        return "PersonView [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + ", password=" + password + ", phone=" + phone + ", address=" + address
                + ", urlAvatar=" + urlAvatar + ", permissionGranted=" + permissionGranted + "]";
    }
    
}
