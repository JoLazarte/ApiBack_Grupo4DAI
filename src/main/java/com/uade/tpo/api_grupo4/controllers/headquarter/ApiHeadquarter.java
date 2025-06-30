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
import com.uade.tpo.api_grupo4.controllers.courseSchedule.CourseScheduleView;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseException;
import com.uade.tpo.api_grupo4.exceptions.CourseScheduleException;
import com.uade.tpo.api_grupo4.exceptions.HeadquarterException;

import jakarta.mail.Header;
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

   
    
    @PostMapping("/createCourseSchedule")
    public ResponseEntity<ResponseData<?>> createSede(@RequestBody HeadquarterView headquarterView) {
         try {
            headquarterView.setId(null);

            Headquarter sede = headquarterView.toEntity();

            Headquarter createdSede = controlador.saveSede(sede);

            HeadquarterView createdSedeView = createdSede.toView();

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdSedeView));

        } catch (Exception error) {
        System.out.printf("[ApiHead.createSede] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear la sede"));
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateSede(@RequestBody HeadquarterView headquarterView) {
        try {
        Headquarter sede = headquarterView.toEntity();
        Headquarter updatedSede = controlador.updateSede(sede);
        HeadquarterView updatedSedeView = updatedSede.toView();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(updatedSedeView));

        }catch (HeadquarterException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[Apiheadquarter.updateSede] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar la sede"));
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

    @PutMapping("/{sedeId}/{courseId}")
    public ResponseEntity<ResponseData<?>> cargarSedesParaCurso(@PathVariable Long sedeId, @PathVariable Long courseId) {
        try {

        controlador.cargarSede(sedeId, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(null));

        }catch (HeadquarterException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[ApiHeadquarter.cargarSedesParaCurso] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo seleccionar la sede"));
        }
    }
    
}