package com.uade.tpo.api_grupo4.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.api_grupo4.DAOs.StudentDAO;
import com.uade.tpo.api_grupo4.DAOs.UserDAO;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.StudentException;
import com.uade.tpo.api_grupo4.exceptions.UserException;
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
    UserDAO userDAO;
    @Autowired
    StudentDAO studentDAO;

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
	public void agregarEstudiante(Long studentId, int cardNumber, String dniFrente, String dniDorso, int nroTramite, int cuentaCorriente) throws StudentException {
		//necesito corroborar que el estudiante haya completado la primera parte del registro, es decir que ya exista en BD
		if (!studentRepository.existsById(studentId)) {
			throw new StudentException("No existe un estudiante con el id " + studentId);
		}
		Student nuevoEstuadiante = new Student(new ArrayList<>(), cardNumber, dniFrente, dniDorso, nroTramite, cuentaCorriente);
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

		student.setPassword(newPassword);
		studentRepository.save(student);
		System.out.println("Contraseña actualizada para el estudiante: " + studentId);
	}

    public Student findStudentByUsername(String username){

        return studentRepository.findByUsername(username);
    }
	
	public boolean loginEstudiante(String username, String password) {
		Student student = findStudentByUsername(username);
		if (student != null && student.getPassword().equals(password)) {
			return true;
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

	public void crearUsuarioGeneral(String username, String firstName, String lastName, String email, String password,
            String phone, String address, String urlAvatar, Boolean permissionGranted 
			//, int cardNumber, String dniFrente, String dniDorso, int nroTramite, int cuentaCorriente 
	) throws UserException {
		if (userRepository.existsByUsername(username) || studentRepository.existsByUsername(username)) {
			throw new UserException("Ya existe un usuario o estudiante con el nombre de usuario: " + username);
		}
		//permissionGranted seria como el rol: si el usurio quiere registrarse como estudiante directamente, permissionGranted es false
		if(permissionGranted == false){
			Student nuevoEstudiante = new Student(null, username, firstName, lastName,  email, password,
            phone,  address,  urlAvatar,  false);
			studentRepository.save(nuevoEstudiante);
			System.out.println("Estudiante agregado: " + nuevoEstudiante.getId());
			//luego se llama la funcion agregarEstudiante(args);
		}
		else {
			User nuevoUsuario = new User( null, username, firstName, lastName,  email, password,
            phone,  address,  urlAvatar,  true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	public boolean loginUsuario(String username, String password) {
		User usuario = findUserByUsername(username);
		if (usuario != null && usuario.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	

	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
