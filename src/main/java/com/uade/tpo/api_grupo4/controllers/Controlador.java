package com.uade.tpo.api_grupo4.controllers;

import com.uade.tpo.api_grupo4.controllers.person.AuthenticationResponse;
import com.uade.tpo.api_grupo4.controllers.person.LoginRequest;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseAttended;
import com.uade.tpo.api_grupo4.entity.CourseMode;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.PendingUser;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.CourseException;
import com.uade.tpo.api_grupo4.exceptions.CourseScheduleException;
import com.uade.tpo.api_grupo4.exceptions.HeadquarterException;
import com.uade.tpo.api_grupo4.exceptions.StudentException;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.CourseAttendRepository;
import com.uade.tpo.api_grupo4.repository.CourseRepository;
import com.uade.tpo.api_grupo4.repository.CourseScheduleRepository;
import com.uade.tpo.api_grupo4.repository.HeadquarterRepository;
import com.uade.tpo.api_grupo4.repository.PendingUserRepository;
import com.uade.tpo.api_grupo4.repository.RecipeRepository;
import com.uade.tpo.api_grupo4.repository.StudentRepository;
import com.uade.tpo.api_grupo4.repository.UserRepository;
import com.uade.tpo.api_grupo4.service.EmailService;
import com.uade.tpo.api_grupo4.service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class Controlador {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final CourseScheduleRepository courseSchedRepository;
	private final HeadquarterRepository headquarterRepository;
	private final CourseAttendRepository courseAttendRepository;
	private final RecipeRepository recipeRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PendingUserRepository pendingUserRepository;
	private final EmailService emailService;

	//-----------------------------------------------Metodos Publicos--------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean aliasExists(String alias) {
		return userRepository.existsByUsername(alias) || studentRepository.existsByUsername(alias);
	}

	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email) || studentRepository.existsByEmail(email);
	}

	//-----------------------------------------------Registro y Login--------------------------------------------------------------------------------------------------------------------------------------------------------


	//--------Registro--------
	public void crearUsuarioGeneral(RegisterRequest request) throws UserException {
		if (userRepository.existsByUsername(request.getUsername()) || studentRepository.existsByUsername(request.getUsername())) {
			throw new UserException("El nombre de usuario '" + request.getUsername() + "' ya está en uso.");
		}
		if (userRepository.existsByEmail(request.getEmail()) || studentRepository.existsByEmail(request.getEmail())) {
			throw new UserException("El correo electrónico '" + request.getEmail() + "' ya está registrado.");
		}
		if (request.getPermissionGranted() == true) {
			User nuevoUsuario = User.builder()
					.username(request.getUsername())
					.email(request.getEmail())
					.password(request.getPassword())
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

	//--------Login--------
	public AuthenticationResponse loginUsuario(LoginRequest request) throws Exception {

		User user = userRepository.findByUsername(request.getUsername());

		if (user != null) {
			if (user.getPassword().equals(request.getPassword())) {
				String jwtToken = jwtService.generateToken(user);
				return AuthenticationResponse.builder().token(jwtToken).build();
			}
		} else {
			Student student = studentRepository.findByUsername(request.getUsername());

			if (student != null) {
				if (student.getPassword().equals(request.getPassword())) {
					String jwtToken = jwtService.generateToken(student);
					return AuthenticationResponse.builder().token(jwtToken).build();
				}
			}
		}

		throw new Exception("Credenciales incorrectas");
	}

	//--------Iniciar Registro--------

	public void iniciarRegistro(RegisterRequest request) throws UserException {
		// Las validaciones de alias y email existentes se mantienen
		if (aliasExists(request.getUsername())) {
			throw new UserException("El nombre de usuario '" + request.getUsername() + "' ya está en uso.");
		}
		if (emailExists(request.getEmail())) {
			throw new UserException("El correo electrónico '" + request.getEmail() + "' ya está registrado.");
		}

		// 1. Generamos un código aleatorio de 4 dígitos
		String code = String.format("%04d", new Random().nextInt(10000));

		// 2. Creamos el objeto de usuario pendiente con todos los datos
		PendingUser pendingUser = PendingUser.builder()
				.username(request.getUsername()).email(request.getEmail()).password(request.getPassword())
				.firstName(request.getFirstName()).lastName(request.getLastName()).phone(request.getPhone())
				.address(request.getAddress()).urlAvatar(request.getUrlAvatar())
				.permissionGranted(request.getPermissionGranted())
				.cardNumber(request.getCardNumber()).nroTramite(request.getNroTramite())
				.nroDocumento(request.getNroDocumento()).tipoTarjeta(request.getTipoTarjeta())
				.dniFrente(request.getDniFrente()).dniDorso(request.getDniDorso())
				.verificationCode(code)
				.expiryDate(LocalDateTime.now().plusMinutes(15)) // El código expira en 15 minutos
				.build();

		// 3. Guardamos el registro pendiente en la nueva tabla
		pendingUserRepository.save(pendingUser);

		// 4. Enviamos el correo
		emailService.sendVerificationCode(pendingUser.getEmail(), code);
	}

	//--------Finalizar Registro--------
	// (Necesitaremos un nuevo DTO para la petición, por ahora usamos los parámetros directamente)
	public void finalizarRegistro(String email, String code) throws UserException {
		// 1. Buscamos el registro pendiente
		PendingUser pendingUser = pendingUserRepository.findById(email)
				.orElseThrow(() -> new UserException("No se encontró un registro pendiente para este email. Puede que haya expirado."));

		// 2. Verificamos si el código ha expirado
		if (pendingUser.getExpiryDate().isBefore(LocalDateTime.now())) {
			pendingUserRepository.delete(pendingUser); // Lo borramos si expiró
			throw new UserException("El código de verificación ha expirado. Por favor, intenta registrarte de nuevo.");
		}

		// 3. Verificamos si el código es correcto
		if (!pendingUser.getVerificationCode().equals(code)) {
			throw new UserException("El código de verificación es incorrecto.");
		}

		// 4. --- ESTE ES EL ARREGLO ---
		// Creamos el objeto final y copiamos TODOS los datos desde el usuario pendiente
		RegisterRequest finalRequest = new RegisterRequest();
		finalRequest.setUsername(pendingUser.getUsername());
		finalRequest.setEmail(pendingUser.getEmail());
		finalRequest.setPassword(pendingUser.getPassword());
		finalRequest.setFirstName(pendingUser.getFirstName());
		finalRequest.setLastName(pendingUser.getLastName());
		finalRequest.setPhone(pendingUser.getPhone());
		finalRequest.setAddress(pendingUser.getAddress());
		finalRequest.setUrlAvatar(pendingUser.getUrlAvatar());
		finalRequest.setPermissionGranted(pendingUser.getPermissionGranted());
		finalRequest.setCardNumber(pendingUser.getCardNumber());
		finalRequest.setDniFrente(pendingUser.getDniFrente());
		finalRequest.setDniDorso(pendingUser.getDniDorso());
		finalRequest.setNroTramite(pendingUser.getNroTramite());
		finalRequest.setNroDocumento(pendingUser.getNroDocumento());
		finalRequest.setTipoTarjeta(pendingUser.getTipoTarjeta());

		// 5. Llamamos a nuestro método de creación original con el objeto ya completo
		crearUsuarioGeneral(finalRequest);

		// 6. Si todo fue bien, borramos el registro pendiente para que no se pueda volver a usar
		pendingUserRepository.delete(pendingUser);
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
					existingStudent.setNroDocumento(student.getNroDocumento());
					existingStudent.setTipoTarjeta(student.getTipoTarjeta());
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

	//-----------------------------------------------Courses--------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<Course> todosLosCursos() throws CourseException {
			List<Course> cursos = courseRepository.findAll();
			if (cursos.isEmpty()) {
				throw new CourseException("No se encontraron cursos en la base de datos.");
			}
			return cursos;
	}

	public Course getCourseByName(String name) throws Exception {
		try{
			return courseRepository.findByName(name).orElseThrow(() -> new CourseException("Curso no encontrado"));
		} catch (CourseException error) {
			throw new CourseException(error.getMessage());
		} catch (Exception error) {
			throw new Exception("[Controlador.getCourseByName] -> " + error.getMessage());
		}
	}

	public void inicializarCursos() throws Exception {
		try{	

        	CourseSchedule courseSchedule1 = new CourseSchedule();
			courseSchedRepository.save(courseSchedule1);
            Course course1 = new Course(null, "Cocina Vegana", "Familiarizate con los principios básicos de la cocina vegana. Descubrí alimentos esenciales en la cocina vegana, como legumbres, frutos secos, semillas, verduras, frutas y granos integrales. Experimenta con diferentes sustituciones de ingredientes para adaptar tus recetas favoritas al estilo vegano.", "No necesitas conocimientos previos.", 120, 600.0, CourseMode.MIXTO, courseSchedule1);
            course1.assignCourseSched(courseSchedule1);

            CourseSchedule courseSchedule2 = new CourseSchedule();
			courseSchedRepository.save(courseSchedule2);
            Course cours2 = new Course(null, "Cocina Asiática", "Comprendé técnicas básicas: Saltear, freír, cocinar al vapor y estofar. Explorá ingredientes clave: Arroz, fideos, soja, diferentes tipos de verduras y especias. Investiga sobre sus usos y combinaciones para enriquecer tus preparaciones. Experimentá con diferentes regiones: Explora platos de China, Japón, Tailandia, Vietnam y otros países ", "Conocimientos básicos de cocina.", 180, 800.0, CourseMode.PRESENCIAL, courseSchedule2);
            course1.assignCourseSched(courseSchedule2);

            CourseSchedule courseSchedule3 = new CourseSchedule();
			courseSchedRepository.save(courseSchedule3);
            Course course3 = new Course(null, "Reposteria Cacera", "Desde tu hogar, aprenderás a conocer y a elegir los ingredientes, como también la utilización de los utensilios, detalles de decoración y la conservación de todas las preparaciones .", "Material de reposteria.", 120, 400.0, CourseMode.VIRTUAL, courseSchedule3);
            course1.assignCourseSched(courseSchedule3);

            courseRepository.save(course1); 
			courseRepository.save(cours2);
			courseRepository.save(course3);

		 } catch (CourseException error) {

        	throw new CourseException(error.getMessage());
      } catch (Exception error) {
				throw new Exception("[Controlador.inicializarCursos] -> " + error.getMessage());
			}
    	}

	//-----------------------------------------------CourseSchedule--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public CourseSchedule completarCronogramaParaCurso(String courseName, CourseSchedule courseSchedule) throws Exception {
		Course cursoExistente = getCourseByName(courseName);
        CourseSchedule courseScheduleAsociado = cursoExistente.getCourseSchedule();
		List<Headquarter> headquarters = todosLasSedes();
		
		return courseSchedRepository.findById(courseScheduleAsociado.getId())
				.map(existingCourseSchedule -> {
					existingCourseSchedule.setHeadquarters(headquarters);
					existingCourseSchedule.setCourse(cursoExistente);
					existingCourseSchedule.setStartDate(courseSchedule.getStartDate());
					existingCourseSchedule.setCompletionDate(courseSchedule.getCompletionDate());
					existingCourseSchedule.setVacancy(courseSchedule.getVacancy());
					
					return courseSchedRepository.save(existingCourseSchedule);
				})
				.orElseThrow(()-> new CourseScheduleException("No existe el cronograma con el id" + courseScheduleAsociado.getId()));
	}

	//-----------------------------------------------Headquarters--------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<Headquarter> todosLasSedes() throws HeadquarterException {
			List<Headquarter> sedes = headquarterRepository.findAll();
			if (sedes.isEmpty()) {
				throw new HeadquarterException("No se encontraron sedes en la base de datos.");
			}
			return sedes;
	}

	public void inicializarSedes() throws Exception {
		try{	
    
            Headquarter headquarter1 = new Headquarter(null, "Caballito","45678889003", "Rosario 789", "sedecaballitotl@gmail.com", "+5491130561833", "20% de reintegro", 0.2, "-70% descuento", 0.7);
            Headquarter headquarter2 = new Headquarter(null, "Devoto", "43445567880", "Chivilcoy 3700", "sededevototl@gmail.com", "+5491120443789", "30% de reintegro", 0.3, "-70% descuento", 0.7);
            Headquarter headquarter3 = new Headquarter(null, "Retiro","44293778034", "Pelegrini 1500", "sederetirotl@gmail.com", "+5491129387029", "25% de reintegro", 0.25, "-60% descuento", 0.6);

            headquarterRepository.save(headquarter1); 
			headquarterRepository.save(headquarter2);
			headquarterRepository.save(headquarter3);

		 } catch (HeadquarterException error) {

        	throw new HeadquarterException(error.getMessage());
      } catch (Exception error) {
				throw new Exception("[Controlador.inicializarCursos] -> " + error.getMessage());
			}
    	}

	public Headquarter getHeadquarterByName(String name) throws Exception {
		try{
			return headquarterRepository.findByName(name).orElseThrow(() -> new HeadquarterException("Sede no encontrada"));
		} catch (HeadquarterException error) {
			throw new HeadquarterException(error.getMessage());
		} catch (Exception error) {
			throw new Exception("[Controlador.getCourseByName] -> " + error.getMessage());
		}
	}

	//-----------------------------------------------CourseAttended--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void seleccionarCursos(Long studentId, Course course){
	
		Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentException("El estudiante con id " + studentId + " no existe."));
		List<Course> cursosDisponibles = todosLosCursos();
		
		if(cursosDisponibles.contains(course)){
			CourseAttended courseAttend = new CourseAttended(null, course.getCourseSchedule(), student);
			
			courseAttendRepository.save(courseAttend);
		
		}
	}

	
}
