package com.uade.tpo.api_grupo4.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.api_grupo4.controllers.person.LoginRequest;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
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

	//-----------------------------------------------Metodos Publicos--------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean aliasExists(String alias) {
		
		return userRepository.existsByUsername(alias) || studentRepository.existsByUsername(alias);
	}

	public boolean emailExists(String email) {
	
		return userRepository.existsByEmail(email) || studentRepository.existsByEmail(email);
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
	public Student agregarEstudiante(Long id, Student student) throws StudentException {
		
		//necesito corroborar que el estudiante haya completado la primera parte del registro, es decir que ya exista en BD
		return studentRepository.findById(id)
						.map(existingStudent -> {
							existingStudent.setAttendedCourses(new ArrayList<>());
							existingStudent.setCardNumber(student.getCardNumber());
							existingStudent.setDniFrente(student.getDniFrente());
							existingStudent.setDniDorso(student.getDniDorso());
							existingStudent.setNroTramite(student.getNroTramite());
							existingStudent.setCuentaCorriente(student.getCuentaCorriente());
							return studentRepository.save(existingStudent);
						})
						.orElseThrow(()-> new StudentException("No existe el estudiante con el id" + id));

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

	public Student findStudentByUsername(String username) {
		return studentRepository.findByUsername(username);
	}

    public Student findStudentByEmail(String email){
        return studentRepository.findByEmail(email);
    }

	public boolean loginEstudiante(LoginRequest loginRequest) throws Exception {
		Student student = findStudentByEmail(loginRequest.getUsername());
		if (student == null) {
			return false;
		}
		return student.getPassword().equals(loginRequest.getPassword());
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

		if (userRepository.existsByEmail(request.getEmail()) || studentRepository.existsByEmail(request.getEmail())) {
			throw new UserException("El correo electrónico '" + request.getEmail() + "' ya está registrado.");
		}

		if (request.getPermissionGranted() == true) {

			User nuevoUsuario = new User(
					null,
					request.getUsername(),
					null, // firstName
					null, // lastName
					request.getEmail(),
					request.getPassword(),
					null, // address
					null, // phone
					null, // url_avatar
					true, // permission_granted
					new ArrayList<>(), // recipes
					new ArrayList<>(), // ratings
					new ArrayList<>()  // saved_recipes
			);
			userRepository.save(nuevoUsuario);
			System.out.println("Usuario agregado con éxito: " + nuevoUsuario.getUsername());
		}

		//permissionGranted seria como el rol: si el usurio quiere registrarse como estudiante directamente, permissionGranted es false
		if(request.getPermissionGranted() == false){
			Student nuevoEstudiante = new Student(null,
					request.getUsername(),
					null, // firstName
					null, // lastName
					request.getEmail(),
					request.getPassword(),
					null, // address
					null, // phone
					null, // url_avatar 
					false, new ArrayList<>(), "", "", "", "", 0);
			studentRepository.save(nuevoEstudiante);
			System.out.println("Estudiante agregado: " + nuevoEstudiante.getId());
			//luego se llama la funcion agregarEstudiante(args);
		}
	}

	public void eliminarUsuario(Long userId) throws UserException {
		User usuario = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));

		userRepository.delete(usuario);
		System.out.println("Usuario eliminado: " + userId);
	}

	public Student cambiarAEstudiante(Long id, Student student) throws Exception{
		Student nuevoEstudiante;
		//necesito corroborar que el usuario haya completado la primera parte del registro, es decir que ya exista en BD
		User usuarioACambiar = userRepository.findById(id).orElseThrow(()-> new UserException("No existe el usuario con el id" + id));
		//tomo los datos de ese usuario para crear un nuevo estudiante:
		nuevoEstudiante = new Student(null, usuarioACambiar.getUsername(), null,null, usuarioACambiar.getEmail(), usuarioACambiar.getPassword(),
        null,  null,  null,  false, new ArrayList<>(), student.getCardNumber(),student.getDniFrente(), student.getDniDorso(), student.getNroTramite(), student.getCuentaCorriente());	
		studentRepository.save(nuevoEstudiante);
		System.out.println("Estudiante agregado: " + nuevoEstudiante.getId());
		eliminarUsuario(id);			
						
		return nuevoEstudiante;
	}

	public void modificarPasswordUsuario(Long userId, String newPassword) throws UserException {
		User usuario = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));

		usuario.setPassword(newPassword);
		userRepository.save(usuario);
		System.out.println("Contraseña actualizada para el usuario: " + userId);
	}
	
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public boolean loginUsuario(LoginRequest loginRequest) {
		// PASO 4: Usamos el userRepository para llamar al método findByUsername
		User usuario = userRepository.findByUsername(loginRequest.getUsername());

		if (usuario == null) {
			return false;
		}
		return usuario.getPassword().equals(loginRequest.getPassword());
	}

	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------Recipes--------------------------------------------------------------------------------------------------------------------------------------------------------

	
}