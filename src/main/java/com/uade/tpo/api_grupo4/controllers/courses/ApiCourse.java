package com.uade.tpo.api_grupo4.controllers.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;

import com.uade.tpo.api_grupo4.entity.Course;

import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseException;
import com.uade.tpo.api_grupo4.exceptions.CourseScheduleException;




@RestController
@RequestMapping("/apiCourse")
public class ApiCourse {
    @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiCourse(Controlador controlador) {
        this.controlador = controlador;
    }

    @GetMapping("/allCourses")
    public ResponseEntity<?> obtenerTodosLosCursos() {
        try {
            List<Course> cursos = controlador.todosLosCursos();
            return ResponseEntity.ok(cursos);
        } catch (CourseException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/initializeCourses")
    public ResponseEntity<ResponseData<String>> initializeCoursesDB() {
        try {

            controlador.inicializarCursos();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Base inicializada correctamente!"));

        } catch (CourseException error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[ApiCourse.initializeCoursesDB] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseData.error("No se pudo inicializar la DB"));
        }
    }

    @PutMapping("{studentId}/{courseId}")
    public ResponseEntity<ResponseData<?>> seleccionarCurso(@PathVariable Long studentId, @PathVariable Long courseId) {
        try {
            
            controlador.seleccionarCursos(studentId, courseId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Curso seleccionado con exito"));

            } catch (CourseException | CourseScheduleException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[ApiCourse.seleccionarCurso] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseData.error("No se pudo seleccionar el curso"));
        }
    }
    


    
}
