package com.uade.tpo.api_grupo4.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.uade.tpo.api_grupo4.controllers.auth.StudentRequest;
import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.service.StudentService;
import com.uade.tpo.api_grupo4.service.UserService;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @PutMapping(path = "/{studentId}", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ResponseData<?>> updateStudent(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody StudentDTO studentDTO) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Student student = authUser.getStudent();
            Long idStudent = student.getId();

            Student studentToUpdate = studentDTO.toEntity();
            student.updateData(studentToUpdate);

            Student studentUpdated = studentService.updateStudent(idStudent, student);
            StudentDTO updatedStudentDTO = studentUpdated.toDTO();
            
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(updatedStudentDTO));

        } catch (UserException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[CartController.addBookToCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo agregar el item al carro"));
        }
    }
    
}
