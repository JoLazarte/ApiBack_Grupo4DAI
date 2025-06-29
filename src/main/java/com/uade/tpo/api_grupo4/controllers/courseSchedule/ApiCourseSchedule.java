package com.uade.tpo.api_grupo4.controllers.courseSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.controllers.courses.CourseView;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseException;
import com.uade.tpo.api_grupo4.exceptions.CourseScheduleException;


@RestController
@RequestMapping("/apiCourseSchedule")
public class ApiCourseSchedule {
    @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiCourseSchedule(Controlador controlador) {
        this.controlador = controlador;
    }

    @PutMapping("/course/{courseName}")
    public ResponseEntity<ResponseData<?>> completarCronogramaParaCurso(@PathVariable String courseName, @RequestBody CourseScheduleView courseScheduleView) {
        try {
            Course cursoExistente = controlador.getCourseByName(courseName);
            CourseSchedule courseSchedule = cursoExistente.getCourseSchedule();
            courseSchedule = courseScheduleView.toEntity();
            CourseSchedule completedCourseSchedule = controlador.completarCronogramaParaCurso(courseName, courseSchedule);
            CourseScheduleView competedCourseScheduleView = completedCourseSchedule.toView();
            
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(competedCourseScheduleView));

            } catch (CourseException | CourseScheduleException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[ApiCourseSchedule.completarCronogramaParaCurso] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseData.error("No se pudo completar el cronograma"));
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateCourseSchedule(@RequestBody CourseScheduleView courseSchedView) {
        try {
        CourseSchedule courseSched = courseSchedView.toEntity();

        CourseSchedule updatedCourseSched = controlador.updateCourseSchedule(courseSched);

        CourseScheduleView updatedCourseSchedView = updatedCourseSched.toView();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(updatedCourseSchedView));

        }catch (CourseScheduleException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[ApiCourseSchedule.updateCourseSchedule] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el cronograma"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<?>> deleteCourseSchedule(@PathVariable Long id) {
        try {
        controlador.deleteCourseSchedule(id);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(null));

        } catch (Exception error) {
        System.out.printf("[ApiCourseSchedule.deleteCourseSchedule] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo eliminar el cronograma"));
        }
    }

    
}
