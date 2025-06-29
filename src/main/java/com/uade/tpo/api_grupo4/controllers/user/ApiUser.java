package com.uade.tpo.api_grupo4.controllers.user;

import java.util.List;

import lombok.Data;
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
import com.uade.tpo.api_grupo4.controllers.person.VerificationRequest;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Intentamos hacer el login. Si tiene éxito, devuelve el token con un 200 OK.
            AuthenticationResponse response = controlador.loginUsuario(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Si loginUsuario lanza la excepción "Credenciales incorrectas", la atrapamos aquí.
            // Devolvemos un error 401 Unauthorized (No Autorizado) con el mensaje de la excepción.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/iniciar-registro")
    public ResponseEntity<String> iniciarRegistro(@RequestBody RegisterRequest request) {
        try {
            controlador.iniciarRegistro(request);
            return ResponseEntity.ok("Código de verificación enviado al correo.");
        } catch (UserException e) {
            // Usamos HttpStatus.CONFLICT (409) si el usuario o email ya existen
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Usamos HttpStatus.INTERNAL_SERVER_ERROR (500) para otros problemas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo: " + e.getMessage());
        }
    }

    @PostMapping("/finalizar-registro")
    public ResponseEntity<String> finalizarRegistro(@RequestBody VerificationRequest request) {
        try {
            controlador.finalizarRegistro(request.getEmail(), request.getCode());
            return ResponseEntity.ok("Usuario registrado y verificado con éxito.");
        } catch (UserException e) {
            // Usamos HttpStatus.BAD_REQUEST (400) si el código es incorrecto, expiró, etc.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al finalizar el registro: " + e.getMessage());
        }
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