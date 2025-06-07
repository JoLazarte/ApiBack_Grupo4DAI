package com.uade.tpo.api_grupo4.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.StudentRepository;



@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    

    public Student createStudent(User user) throws Exception {
			try { 
				Student newStudent = new Student(null, new ArrayList<>(),0, "","",0, 0, user);
        return newStudent;
	
			} catch (UserException error) {
                throw new UserException(error.getMessage());
            } catch (Exception error) {
				throw new Exception("[UserService.createUser] -> " + error.getMessage());
			}
    }

    public Student updateStudent(Student student) throws Exception {
        try {
          return studentRepository.save(student);
        } catch (Exception error) {
          throw new Exception("[UserService.updateUser] -> " + error.getMessage());
        }
    }
    
}
