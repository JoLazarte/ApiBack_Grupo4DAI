package com.uade.tpo.api_grupo4.controllers;
// Nota: Para una mejor organización, esta clase debería estar en un paquete 'service',
// pero la mantendremos aquí para seguir tu estructura actual.

import com.uade.tpo.api_grupo4.controllers.person.AuthenticationResponse;
import com.uade.tpo.api_grupo4.controllers.person.LoginRequest;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.StudentException;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.RecipeRepository;
import com.uade.tpo.api_grupo4.repository.StudentRepository;
import com.uade.tpo.api_grupo4.repository.UserRepository;
import com.uade.tpo.api_grupo4.service.JwtService; // Asegúrate de importar tu JwtService

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // Crea un constructor con todos los campos 'final' para la inyección
public class Controlador {

	// CAMBIO: Inyección de dependencias a través de campos 'final'
	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final RecipeRepository recipeRepository;

	// NUEVO: Inyectamos los servicios que necesitamos para el login JWT
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	// ELIMINADO: El patrón singleton manual (getInstancia, constructor privado, etc.)

	//-----------------------------------------------Metodos Publicos--------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean aliasExists(String alias) {
		return userRepository.existsByUsername(alias) || studentRepository.existsByUsername(alias);
	}

	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email) || studentRepository.existsByEmail(email);
	}

	//-----------------------------------------------Students--------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<Student> todosLosEstudiantes() throws StudentException {
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			throw new StudentException("No se encontraron estudiantes en la base de datos.");
		}
		return students;
	}
	public Student agregarEstudiante(Long id, Student student) throws StudentException {
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
			throw new UserException("El nombre de usuario '" + request.getUsername() + "' ya está en uso.");
		}
		if (userRepository.existsByEmail(request.getEmail()) || studentRepository.existsByEmail(request.getEmail())) {
			throw new UserException("El correo electrónico '" + request.getEmail() + "' ya está registrado.");
		}

		if (request.getPermissionGranted() == true) {
			// Usamos el Builder para construir el objeto de forma más clara
			User nuevoUsuario = User.builder()
					.username(request.getUsername())
					.email(request.getEmail())
					.password(request.getPassword()) // NOTA: Aquí deberías codificar la contraseña antes de guardarla. Lo veremos después.
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.phone(request.getPhone())
					.address(request.getAddress())
					.urlAvatar(request.getUrlAvatar())
					.permissionGranted(true)
					.recipes(new ArrayList<>())
					.savedRecipes(new ArrayList<>())
					.reviews(new ArrayList<>())
					.build();

			userRepository.save(nuevoUsuario);
			System.out.println("Usuario agregado con éxito: " + nuevoUsuario.getUsername());

		} else {
			// También usamos el Builder para el Estudiante
			Student nuevoEstudiante = Student.builder()
					.username(request.getUsername())
					.email(request.getEmail())
					.password(request.getPassword())
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.phone(request.getPhone())
					.address(request.getAddress())
					.urlAvatar(request.getUrlAvatar())
					.permissionGranted(false)
					.attendedCourses(new ArrayList<>())
					.cardNumber(request.getCardNumber())
					.dniFrente(request.getDniFrente())
					.dniDorso(request.getDniDorso())
					.nroTramite(request.getNroTramite())
					.cuentaCorriente(0)
					.nroDocumento(request.getNroDocumento())
					.tipoTarjeta(request.getTipoTarjeta())
					.build();

			studentRepository.save(nuevoEstudiante);
			System.out.println("Estudiante agregado con éxito: " + nuevoEstudiante.getUsername());
		}
	}

	public void eliminarUsuario(Long userId) throws UserException {
		User usuario = userRepository.findById(userId).orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));
		userRepository.delete(usuario);
		System.out.println("Usuario eliminado: " + userId);
	}

	public Student cambiarAEstudiante(Long id, Student student) throws Exception {
		User usuarioACambiar = userRepository.findById(id)
				.orElseThrow(() -> new UserException("No existe el usuario con el id " + id));

		// Usamos el Builder para que el código sea más claro y no dependa del orden del constructor
		Student nuevoEstudiante = Student.builder()
				.username(usuarioACambiar.getUsername())
				.email(usuarioACambiar.getEmail())
				.password(usuarioACambiar.getPassword())
				.permissionGranted(false)
				.attendedCourses(new ArrayList<>())
				.cardNumber(student.getCardNumber())
				.dniFrente(student.getDniFrente())
				.dniDorso(student.getDniDorso())
				.nroTramite(student.getNroTramite())
				.cuentaCorriente(student.getCuentaCorriente())
				// Los nuevos campos (nroDocumento, tipoTarjeta) no se establecen aquí,
				// por lo que serán null por defecto, lo cual es correcto para esta lógica de conversión.
				.build();

		studentRepository.save(nuevoEstudiante);
		System.out.println("Estudiante agregado: " + nuevoEstudiante.getId());
		eliminarUsuario(id);
		return nuevoEstudiante;
	}

	public void modificarPasswordUsuario(Long userId, String newPassword) throws UserException {
		User usuario = userRepository.findById(userId).orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));
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

	public AuthenticationResponse loginUsuario(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		User user = userRepository.findByUsername(request.getUsername());
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}