package com.uade.tpo.api_grupo4.controllers.courses;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseMode;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.Inscripcion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseView {
    private Long id;
    private String name;
    private String content;
    private String requirements;
    private int length;
    private int price;
    private CourseMode mode;
    private String fechaInicio;
    private String fechaFin;
    @JsonIgnore
    private List<Headquarter> sedes;
    

   

    public Course toEntity(){
        return new Course(
            this.id,
            this.name,
            this.content,
            this.requirements,
            this.length,
            this.price,
            this.mode,
            this.fechaInicio,
            this.fechaFin, 
            this.sedes
    
        );
    }
}
