package com.uade.tpo.api_grupo4.controllers.headquarter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseException;
import com.uade.tpo.api_grupo4.exceptions.CourseScheduleException;
import com.uade.tpo.api_grupo4.exceptions.HeadquarterException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apiHeadquarter")
public class ApiHeadquarter {
    @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiHeadquarter(Controlador controlador) {
        this.controlador = controlador;
    }

    @GetMapping("/allHeadquarters")
    public ResponseEntity<?> obtenerTodosLasSedes() {
        try {
            List<Headquarter> sedes = controlador.todosLasSedes();
            return ResponseEntity.ok(sedes);
        } catch (HeadquarterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/initializeHeadquarters")
    public ResponseEntity<ResponseData<String>> initializeHeadquartersDB() {
        try {

            controlador.inicializarSedes();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Base inicializada correctamente!"));

        } catch (HeadquarterException error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[ApiHeadquarter.initializeHeadquartersDB] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseData.error("No se pudo inicializar la DB"));
        }
  }

   @PostMapping
    public ResponseEntity<HeadquarterView> createHeadquarter(@Valid @RequestBody HeadquarterView headquarterView) {
        try {
            HeadquarterView savedHeadquarter = controlador.saveSede(headquarterView);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHeadquarter);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HeadquarterView> updateHeadquarter(@PathVariable Long id, 
                                                           @Valid @RequestBody HeadquarterView headquarterView) {
        try {
            return controlador.updateSede(id, headquarterView)
                    .map(updatedHeadquarter -> ResponseEntity.ok(updatedHeadquarter))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<?>> deleteSede(@PathVariable Long id) {
        try {
        controlador.deleteSede(id);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(null));

        } catch (Exception error) {
        System.out.printf("[ApiHeadquarter.deleteSede] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo eliminar la sede"));
        }
    }

    @GetMapping("/with-courses")
    public ResponseEntity<List<HeadquarterView>> getHeadquartersWithCourses() {
        try {
            List<HeadquarterView> headquarters = controlador.findHeadquartersWithCourses();
            return ResponseEntity.ok(headquarters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
}