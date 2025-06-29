package com.uade.tpo.api_grupo4.controllers.courseSchedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseScheduleView {
    
    private Long id;
    private List<Headquarter> headquarters;
    private Course course;
    private LocalDate startDate;
    private LocalDate completionDate;
    private DayOfWeek diaEnQueSeDicta;
    private int vacancy;

    public CourseSchedule toEntity() {
        return new CourseSchedule(
                this.id,
                this.headquarters,
                this.course,
                this.startDate,
                this.completionDate,
                this.diaEnQueSeDicta,
                this.vacancy
                );
    }
}
