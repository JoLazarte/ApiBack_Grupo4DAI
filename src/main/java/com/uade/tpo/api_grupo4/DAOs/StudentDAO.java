package com.uade.tpo.api_grupo4.DAOs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.repository.StudentRepository;


public class StudentDAO {
    private static StudentDAO instancia;
    @Autowired
    private StudentRepository studentRepository;
    
    private StudentDAO() {}

    public static StudentDAO getInstancia() {
        if (instancia == null) {
            instancia = new StudentDAO();
        }
        return instancia;
    }

    public StudentDAO(StudentRepository estudianteRepositorio) {
        this.studentRepository = estudianteRepositorio;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long studentId) {return studentRepository.findById(studentId);}

    public void save(Student student) {
        studentRepository.save(student);
    }
    public Student update(Student student) {
        return studentRepository.save(student);
    } 

    
    
}