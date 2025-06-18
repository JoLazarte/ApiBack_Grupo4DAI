package com.uade.tpo.api_grupo4.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.api_grupo4.entity.CourseAttended;
import com.uade.tpo.api_grupo4.entity.Person;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Review;
import com.uade.tpo.api_grupo4.entity.SavedRecipe;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.StudentException;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.PersonRepository;
import com.uade.tpo.api_grupo4.repository.StudentRepository;
import com.uade.tpo.api_grupo4.repository.UserRepository;


@Service
public class Controller {
    private static Controller instancia;
    @Autowired
	StudentRepository studentRepository;
	@Autowired
	UserRepository userRepository;
    @Autowired
	PersonRepository personRepository;

    private Controller() { }

	public static Controller getInstancia() {
		if(instancia == null)
			instancia = new Controller();
		return instancia;
	}

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	//-----------------------------------------------Students--------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<Student> todosLosEstudiantes() throws StudentException {
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			throw new StudentException("No se encontraron estudiantes en la base de datos.");
		}
		return students;
	}

	public void agregarEstudiante(Long studentId, List<CourseAttended> attendedCourse, int cardNumber, String dniFrente, String dniDorso, int nroTramite, int cuentaCorriente) throws StudentException {
		if (studentRepository.existsById(studentId)) {
			throw new StudentException("Ya existe un estudiante con el id " + studentId);
		}

		Person persona = personRepository.findById(studentId)
				.orElseThrow(() -> new StudentException("No se encontró una persona con el id " + studentId));

		Student nuevoEstuadiante = new Student(studentId, attendedCourse, cardNumber, dniFrente, dniDorso, nroTramite, cuentaCorriente, persona);
		studentRepository.save(nuevoEstuadiante);

		System.out.println("Estudiante agregado: " + nuevoEstuadiante.getId());
	}

	public void eliminarEstudiante(Long studentId) throws StudentException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentException("El estudiante con id " + studentId + " no existe."));

		studentRepository.delete(student);
		System.out.println("Estudiante eliminado: " + studentId);
	}

	public void modificarPasswordStudent(Long studentId, String newPassword) throws StudentException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentException("El estudiante con id " + studentId + " no existe."));

		student.getPersona().setPassword(newPassword);
		studentRepository.save(student);
		System.out.println("Contraseña actualizada para el estudiante: " + studentId);
	}
    public Student findStudentByUsername(String username){

        return studentRepository.findByUsername(username).orElse(null);
    }



    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------Users--------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<User> todosLosUsuarios() throws UserException {
		List<User> usuarios = userRepository.findAll();
		if (usuarios.isEmpty()) {
			throw new UserException("No se encontraron usuarios en la base de datos.");
		}
		return usuarios;
	}

	public void crearUsuario(Long userId, List<SavedRecipe> savedRecipes, List<Recipe>recipes, List<Review>reviews , String password) throws UserException {
		if (userRepository.existsById(userId)) {
			throw new UserException("Ya existe un usuario con el id " + userId);
		}
		Person persona = personRepository.findById(userId)
				.orElseThrow(() -> new UserException("No se encontró una persona con el id " + userId));
		User nuevoUsuario = new User(userId, savedRecipes, recipes, reviews, persona);

		userRepository.save(nuevoUsuario);

		System.out.println("Usuario agregado: " + nuevoUsuario.getId());
	}

	public void eliminarUsuario(Long userId) throws UserException {
		User usuario = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));

		userRepository.delete(usuario);
		System.out.println("Usuario eliminado: " + userId);
	}

	public void modificarPasswordUsuario(Long userId, String newPassword) throws UserException {
		User usuario = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));

		usuario.getPersona().setPassword(newPassword);
		userRepository.save(usuario);
		System.out.println("Contraseña actualizada para el usuario: " + userId);
	}

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

	public boolean loginUsuario(String username, String password) {
		User usuario = findUserByUsername(username);
		if (usuario != null && usuario.getPersona().getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public boolean loginEstudiante(String username, String password) {
		Student student = findStudentByUsername(username);
		if (student != null && student.getPersona().getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
