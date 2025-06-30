package com.uade.tpo.api_grupo4.controllers.courses;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequest {
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long studentId;
    
    @NotNull(message = "El ID del cronograma es obligatorio")
    private Long courseScheduleId;
    
}

