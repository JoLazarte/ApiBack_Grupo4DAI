package com.uade.tpo.api_grupo4.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.api_grupo4.controllers.person.LoginRequest;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.RecipeException;
import com.uade.tpo.api_grupo4.exceptions.StudentException;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.RecipeRepository;
import com.uade.tpo.api_grupo4.repository.StudentRepository;
import com.uade.tpo.api_grupo4.repository.UserRepository;


@Service
public class Controlador {
    private static Controlador instancia;
    @Autowired
	StudentRepository studentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RecipeRepository recipeRepository;

    private Controlador() { }

	public static Controlador getInstancia() {
		if(instancia == null)
			instancia = new Controlador();
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
	//una vez que ya se ha completado la primera parte del registro:
	public Student agregarEstudiante(Long studentId, Student student) throws StudentException {
		//necesito corroborar que el estudiante haya completado la primera parte del registro, es decir que ya exista en BD
		return studentRepository.findById(studentId)
						.map(existingStudent -> {
							existingStudent.setAttendedCourses(new ArrayList<>());
							existingStudent.setCardNumber(student.getCardNumber());
							existingStudent.setDniFrente(student.getDniFrente());
							existingStudent.setDniDorso(student.getDniDorso());
							existingStudent.setNroTramite(student.getNroTramite());
							existingStudent.setCuentaCorriente(student.getCuentaCorriente());
							return studentRepository.save(existingStudent);
						})
						.orElseThrow(()-> new StudentException("No existe el estudiante con el id" + studentId));
		
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

		student.setPassword(newPassword);
		studentRepository.save(student);
		System.out.println("Contraseña actualizada para el estudiante: " + studentId);
	}

	//paso previo a la registracion o para logearse:
    public Student findStudentByEmail(String email){
        return studentRepository.findByEmail(email);
    }

	public boolean loginEstudiante(LoginRequest loginRequest) throws Exception {
		Student student = findStudentByEmail(loginRloginRequest.getUsername());
		//logeo existoso
		if (student != null && student.getPassword().equals(loginRequest.getPassword())) {
			return true;
		}
		//el estudiante ya esta registrado pero no coincide su password:
		if (student !=null && student.getPassword() != loginRequest.getPassword()){
			throw new StudentException("Ya existe un estudiante registrado con el nombre de usuario: " + loginRequest.getPassword());
		}
		return false;
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

	public void crearUsuarioGeneral(RegisterRequest request) throws UserException {
		if (userRepository.existsByUsername(request.getUsername()) || studentRepository.existsByUsername(request.getUsername())) {
			throw new UserException("Ya existe un usuario o estudiante con el nombre de usuario: " + request.getUsername());
		}
		//permissionGranted seria como el rol: si el usurio quiere registrarse como estudiante directamente, permissionGranted es false
		if(request.getPermissionGranted() == false){
			Student nuevoEstudiante = new Student(null, request.getUsername(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(),
            request.getPhone(),  request.getAddress(),  request.getUrlAvatar(),  false, new ArrayList<>(), "", "", "", "", 0);
			studentRepository.save(nuevoEstudiante);
			System.out.println("Estudiante agregado: " + nuevoEstudiante.getId());
			//luego se llama la funcion agregarEstudiante(args);
		}
		if(request.getPermissionGranted() == true) {
			User nuevoUsuario = new User( null, request.getUsername(), request.getFirstName(), request.getLastName(),  request.getEmail(), request.getPassword(),
            request.getPhone(),  request.getAddress(),  request.getUrlAvatar(),  true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
			userRepository.save(nuevoUsuario);
			System.out.println("Usuario agregado: " + nuevoUsuario.getId());
		}	
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

		usuario.setPassword(newPassword);
		userRepository.save(usuario);
		System.out.println("Contraseña actualizada para el usuario: " + userId);
	}
	//paso previo a la registracion o para logearse:
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

	public boolean loginUsuario(LoginRequest loginRequest) throws Exception {
		User usuario = findUserByEmail(loginRequest.getUsername());
		//logeo existoso:
		if (usuario != null && usuario.getPassword().equals(loginRequest.getPassword())) {
			return true;
		}
		//el user ya esta registrado pero no coincide su password:
		if (usuario !=null && usuario.getPassword() != loginRequest.getPassword()){
			throw new UserException("Ya existe un usuario registrado con el nombre de usuario: " + loginRequest.getPassword());
		}
		return false;
	}

	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------Recipes--------------------------------------------------------------------------------------------------------------------------------------------------------

	
}
