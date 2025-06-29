package com.uade.tpo.api_grupo4.controllers.courseSchedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.EnumDiaSemana;
import com.uade.tpo.api_grupo4.entity.Headquarter;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseScheduleView {
    
    private Long id;
    private Course course;
    private int vacancy;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int diaEnQueSeDicta;
    private String instructor;
    
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
