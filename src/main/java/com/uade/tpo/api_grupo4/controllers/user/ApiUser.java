package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.UserException;

@RestController
@RequestMapping("/users")
public class ApiUser {
    @Autowired
    private Controlador controlador;

    @GetMapping("/allUsers")
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        try {
            List<User> usuarios = controlador.todosLosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> agregarUsuarioGeneral(@PathVariable String username, @PathVariable String firstName, @PathVariable String lastName, 
           @PathVariable String email, @PathVariable String password, @PathVariable String phone, 
           @PathVariable String address, @PathVariable String urlAvatar, @PathVariable Boolean permissionGranted) {
        try {
            controlador.crearUsuarioGeneral(username, firstName, lastName, email, password, phone, address, urlAvatar, permissionGranted);
            return ResponseEntity.ok("Usuario agregado con éxito.");
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Boolean> login(@PathVariable String username, @PathVariable String password) {
        boolean resultado = controlador.loginUsuario(username, password);
        return ResponseEntity.ok(resultado);
    }



    
}
