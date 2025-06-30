package com.uade.tpo.api_grupo4.controllers.courses;

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

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseMode;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseException;


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
   
    //Se puede crear un curso nuevo con un body por postman:
    @PostMapping("")
    public ResponseEntity<ResponseData<?>> createCourse(@RequestBody CourseView courseView) {
        try {
        courseView.setId(null);

        Course course = courseView.toEntity();

        Course createdCourse = controlador.createCourse(course);

        CourseView createdCourseView = createdCourse.toView();

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdCourseView));

        } catch (Exception error) {
        System.out.printf("[ApiCourse.createCourse] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear el curso"));
        }
    }

    //cursos ya creados en el controlador:
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

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateCourse(@RequestBody CourseView courseView) {
        try {
        Course course = courseView.toEntity();
        Course updatedCourse = controlador.updateCourse(course);
        CourseView updatedCourseView = updatedCourse.toView();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(updatedCourseView));

        }catch (CourseException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[ApiCourse.updateCourse] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el curso"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<?>> deleteCourse(@PathVariable Long id) {
        try {
        controlador.deleteCourse(id);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(null));

        } catch (Exception error) {
        System.out.printf("[ApiCourse.deleteCourse] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo eliminar el producto"));
        }
    }

    @GetMapping("/by-mode/{mode}")
    public ResponseEntity<List<CourseView>> getCoursesByMode(@PathVariable CourseMode mode) {
        try {
            List<CourseView> courses = controlador.findByMode(mode);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    

    
}