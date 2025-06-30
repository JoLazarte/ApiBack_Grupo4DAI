package com.uade.tpo.api_grupo4.controllers.courseSchedule;

import java.time.LocalTime;


import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseScheduleView {
    
    private Long id;
    private Course course;
 
    private String horaInicio;
    private String horaFin;
    
    private String instructor;
    private int vacancy;
    private int diaEnQueSeDicta;
    
    public CourseSchedule toEntity() {
        return new CourseSchedule(
                this.id,
                this.course,
                this.horaInicio,
                this.horaFin,
                this.instructor,
                this.vacancy,
                this.diaEnQueSeDicta
                );
    }
}
