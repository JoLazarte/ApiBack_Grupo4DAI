package com.uade.tpo.api_grupo4.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.api_grupo4.controllers.auth.RegisterRequest;
import com.uade.tpo.api_grupo4.entity.Role;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
//import com.uade.tpo.api_grupo4.exceptions.RoleException;
import com.uade.tpo.api_grupo4.exceptions.UserException;

import com.uade.tpo.api_grupo4.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    //@Transactional
    public User createUser(RegisterRequest request) throws Exception {
			try {
				boolean userExist = userRepository.existsByUsername(request.getUsername());
          if(userExist) throw new UserException("El usuario " + request.getUsername() + " ya existe");
            userExist = userRepository.existsByEmail(request.getEmail());
          if(userExist) throw new UserException("El email " + request.getEmail() + " ya esta registrado.");
        
        Student student = studentService.createStudent();   
				User user = new User(null,request.getUsername(), request.getFirstName(), request.getLastName(),
								request.getEmail(),
								passwordEncoder.encode(request.getPassword()),
                request.getPhone(), request.getAddress(), request.getRole(), request.getUrlAvatar(), 1, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
        
        if(request.getRole()==Role.STUDENT){
                  user.setPermissionGranted(false);
                  user.setUserStatus(2);
                  
                  user.setStudent(student);
                  user.assignStudent(student);
                  student.setUser(user);
                  student.assignUser(user);
                  //System.out.println("Por favor complete su registro como estudiante.");
                 
                }  
               return userRepository.save(user);
        	
			} catch (UserException error) {
                throw new UserException(error.getMessage());
            } catch (Exception error) {
				throw new Exception("[UserService.createUser] -> " + error.getMessage());
			}
    }
    public User getUserByUsername(String username) throws Exception {
        try {
          return userRepository.findByUsername(username).orElseThrow(() -> new UserException("Usuario no encontrado"));
        } catch (UserException error) {
          throw new UserException(error.getMessage());
        } catch (Exception error) {
          throw new Exception("[UserService.getUserByUsername] -> " + error.getMessage());
        }
    }
}
