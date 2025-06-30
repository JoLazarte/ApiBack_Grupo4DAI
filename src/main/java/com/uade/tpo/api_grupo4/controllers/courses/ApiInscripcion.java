package com.uade.tpo.api_grupo4.controllers.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;

@RestController
@RequestMapping("/apiInscripcion")
public class ApiInscripcion {
     @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiInscripcion(Controlador controlador) {
        this.controlador = controlador;
    }

    @PostMapping("/enroll/{studentId}/{scheduleId}")
    public ResponseEntity<InscripcionView> enrollStudent(@PathVariable(value = "studentId", required = false) Long studentId, @PathVariable(value = "scheduleId") Long scheduleId) {
        try {
            InscripcionView inscription = controlador.enrollStudent(studentId, scheduleId);
            return ResponseEntity.status(HttpStatus.CREATED).body(inscription);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<InscripcionView> cancelEnrollment(@PathVariable Long id) {
        try {
            return controlador.cancelEnrollment(id)
                    .map(inscription -> ResponseEntity.ok(inscription))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<InscripcionView>> getInscriptionsByStudent(@PathVariable Long studentId) {
        try {
            List<InscripcionView> inscriptions = controlador.findByStudent(studentId);
            return ResponseEntity.ok(inscriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
