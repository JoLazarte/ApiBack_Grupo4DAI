package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.UserException;

@RestController
@RequestMapping("/apiUser")
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
    public ResponseEntity<String> agregarUsuarioGeneral(@RequestBody RegisterRequest request) {
        try { 
            controlador.crearUsuarioGeneral(request);
            return ResponseEntity.ok("Usuario agregado con éxito.");
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Boolean> login(@RequestParam String username, @RequestParam String password) {
        boolean resultado = controlador.loginUsuario(username, password);
        return ResponseEntity.ok(resultado);
    }



    
}
