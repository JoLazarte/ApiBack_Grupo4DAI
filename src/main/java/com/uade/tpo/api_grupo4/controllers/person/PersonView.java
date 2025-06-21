package com.uade.tpo.api_grupo4.controllers.person;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonView {
    protected Long id;
    @NotNull
    protected String username;
    protected String firstName; 
    protected String lastName;
    @NotNull
    protected String email;
    @NotNull
    protected String password;
    private String phone;
    protected String address;
    protected String urlAvatar;
    @NotNull
    protected Boolean permissionGranted;
          
}