package com.uade.tpo.api_grupo4.controllers.courseSchedule;

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
import com.uade.tpo.api_grupo4.controllers.courses.CourseView;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseException;
import com.uade.tpo.api_grupo4.exceptions.CourseScheduleException;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/apiCourseSchedule")
public class ApiCourseSchedule {
    @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiCourseSchedule(Controlador controlador) {
        this.controlador = controlador;
    }

    @PostMapping
    public ResponseEntity<CourseScheduleView> createSchedule(@Valid @RequestBody CourseScheduleView scheduleView) {
        try {
            CourseScheduleView savedSchedule = controlador.saveCronograma(scheduleView);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseScheduleView> updateSchedule(@PathVariable Long id, 
                                                           @Valid @RequestBody CourseScheduleView scheduleView) {
        try {
            return controlador.updateCronograma(id, scheduleView)
                    .map(updatedSchedule -> ResponseEntity.ok(updatedSchedule))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<CourseScheduleView>> getSchedulesByCourse(@PathVariable Long courseId) {
        try {
            List<CourseScheduleView> schedules = controlador.findSchedByCourse(courseId);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
}