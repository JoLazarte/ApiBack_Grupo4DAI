package com.uade.tpo.api_grupo4.controllers.courseAttend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.CourseAttendException;


@RestController
@RequestMapping("/apiCourseAttend")
public class ApiCourseAttend {
    @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiCourseAttend(Controlador controlador) {
        this.controlador = controlador;
    }

    
}
