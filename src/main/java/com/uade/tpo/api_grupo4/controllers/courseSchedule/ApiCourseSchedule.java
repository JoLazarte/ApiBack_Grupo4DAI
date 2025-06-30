package com.uade.tpo.api_grupo4.controllers.courseSchedule;

import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;

import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseException;



@RestController
@RequestMapping("/apiCourseSchedule")
public class ApiCourseSchedule {
    @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiCourseSchedule(Controlador controlador) {
        this.controlador = controlador;
    }

  
    @PostMapping("/createSchedule/{courseId}/{sedeId}")
    public ResponseEntity<ResponseData<?>> createSchedule(@PathVariable Long courseId, @PathVariable Long sedeId, @RequestBody CourseScheduleView scheduleView) {
         try {
            scheduleView.setId(null);

            CourseSchedule courseSched = scheduleView.toEntity();

            CourseSchedule createdCourseSched = controlador.saveCronograma(courseId, sedeId,courseSched);

            CourseScheduleView createdCourseSchedView = createdCourseSched.toView();

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdCourseSchedView));

        } catch (Exception error) {
        System.out.printf("[ApiCourseSchedule.createCourseSchedule] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear el cronograma"));
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateSchedule(@RequestBody CourseScheduleView courseScheduleView) {
        try {
        CourseSchedule courseSchedule = courseScheduleView.toEntity();
        CourseSchedule updatedCourseSched = controlador.updateCronograma(courseSchedule);
        CourseScheduleView updatedCourseSchedView = updatedCourseSched.toView();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(updatedCourseSchedView));

        }catch (CourseException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[ApiCourseSchedule.updateSchedule] -> %s", error.getMessage() );
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