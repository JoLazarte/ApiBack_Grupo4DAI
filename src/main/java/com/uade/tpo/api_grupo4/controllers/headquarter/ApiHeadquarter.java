package com.uade.tpo.api_grupo4.controllers.headquarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.HeadquarterException;

@RestController
@RequestMapping("/apiHeadquarter")
public class ApiHeadquarter {
    @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiHeadquarter(Controlador controlador) {
        this.controlador = controlador;
    }



    @PostMapping("/initializeHeadquarters")
    public ResponseEntity<ResponseData<String>> initializeHeadquartersDB() {
        try {

            controlador.inicializarSedes();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Base inicializada correctamente!"));

        } catch (HeadquarterException error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[ApiCourse.initializeHeadquartersDB] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseData.error("No se pudo inicializar la DB"));
        }
  }
}
