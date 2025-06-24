package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.controllers.person.LoginRequest;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.StudentException;
import com.uade.tpo.api_grupo4.exceptions.UserException;

import com.uade.tpo.api_grupo4.controllers.person.AuthenticationResponse;



@RestController
@RequestMapping("/apiUser")
public class ApiUser {
    @Autowired
    private Controlador controlador;

    @Autowired
    public ApiUser(Controlador controlador) {
        this.controlador = controlador;
    }

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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @GetMapping("/check-alias")
    public ResponseEntity<Boolean> checkAlias(@RequestParam String alias) {
        boolean exists = controlador.aliasExists(alias);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = controlador.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        // Ahora la línea es más simple: solo llama al método del controlador
        // y devuelve directamente la respuesta que este le da.
        return ResponseEntity.ok(controlador.loginUsuario(loginRequest));
    }

    @PostMapping("/toStudent")
    public ResponseEntity<String> cambiarAEstudiante(@PathVariable Long userId, @RequestBody Student student) {
        try {
            controlador.cambiarAEstudiante(userId, student);
            return ResponseEntity.ok("Usuario de id: " + userId + " cambiando a Estudiante con éxito.");
        } catch (StudentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }
      
}