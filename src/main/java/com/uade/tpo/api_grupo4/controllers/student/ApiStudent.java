package com.uade.tpo.api_grupo4.controllers.student;

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
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.exceptions.StudentException;

@RestController
@RequestMapping("/students")
public class ApiStudent {
    @Autowired
    private Controlador controlador;
    
    @GetMapping("/allStudents")
    public ResponseEntity<?> obtenerTodosLosEstudiantes() {
        try {
            List<Student> estudiantes = controlador.todosLosEstudiantes();
            return ResponseEntity.ok(estudiantes);
        } catch (StudentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/createStudent")
    public ResponseEntity<String> crearEmpleado(@PathVariable Long studentId, @PathVariable int cardNumber, @PathVariable String dniFrente, 
            @PathVariable String dniDorso, @PathVariable int nroTramite, @PathVariable int cuentaCorriente) {
        try {
            controlador.agregarEstudiante(studentId, cardNumber, dniFrente, dniDorso, nroTramite, cuentaCorriente);
            return ResponseEntity.ok("Empleado creado exitosamente con el id: " + studentId);
        } catch (StudentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/loginStudent")
    public ResponseEntity<Boolean> login(@PathVariable String username, @PathVariable String password) {
        boolean resultado = controlador.loginEstudiante(username, password);
        return ResponseEntity.ok(resultado);
    }
}
