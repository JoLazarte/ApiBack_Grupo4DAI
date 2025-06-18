package com.uade.tpo.api_grupo4.service;

import java.util.ArrayList;
//import java.util.Optional;
//import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.uade.tpo.api_grupo4.controllers.auth.StudentRequest;
import com.uade.tpo.api_grupo4.entity.Student;
//import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.StudentRepository;




@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
 
    public Student createStudent() throws Exception {
			try { 
				Student newStudent = new Student( null, new ArrayList<>(),0, " "," ",0, 0, null);
        return newStudent;
	
			} catch (UserException error) {
                throw new UserException(error.getMessage());
            } catch (Exception error) {
				throw new Exception("[UserService.createUser] -> " + error.getMessage());
			}
    }

    public Student updateStudent(Long studentId, Student studentRequest) throws Exception {
        try {
          Student student = studentRepository.findById(studentId).orElse(null);

          student.setCardNumber(studentRequest.getCardNumber());
          student.setDniFrente(studentRequest.getDniFrente());
          student.setDniDorso(studentRequest.getDniDorso());
          student.setNroTramite(studentRequest.getNroTramite());
          student.setCuentaCorriente(studentRequest.getCuentaCorriente());
          return studentRepository.save(student);
      
    
          
        } catch (Exception error) {
          throw new Exception("[StudentService.updateStudent] -> " + error.getMessage());
        }
    }
    
}
