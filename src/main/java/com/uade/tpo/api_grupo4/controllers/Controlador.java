package com.uade.tpo.api_grupo4.controllers;

import com.uade.tpo.api_grupo4.controllers.courseSchedule.CourseScheduleView;
import com.uade.tpo.api_grupo4.controllers.courses.CourseView;
import com.uade.tpo.api_grupo4.controllers.courses.InscripcionView;
import com.uade.tpo.api_grupo4.controllers.person.AuthenticationResponse;
import com.uade.tpo.api_grupo4.controllers.person.LoginRequest;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateRecipeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateReviewRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateTypeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateUnitRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.MaterialRequestDTO;
import com.uade.tpo.api_grupo4.controllers.recipe.StepRequestDTO;
import com.uade.tpo.api_grupo4.controllers.user.BecomeStudentRequest;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseMode;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.Ingredient;
import com.uade.tpo.api_grupo4.entity.Inscripcion;
import com.uade.tpo.api_grupo4.entity.MaterialUsed;
import com.uade.tpo.api_grupo4.entity.PendingUser;
import com.uade.tpo.api_grupo4.entity.Person;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Review;
import com.uade.tpo.api_grupo4.entity.Step;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.TypeOfRecipe;
import com.uade.tpo.api_grupo4.entity.Unit;
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
import com.uade.tpo.api_grupo4.repository.InscripcionRepository;
import com.uade.tpo.api_grupo4.repository.IngredientRepository;
import com.uade.tpo.api_grupo4.repository.MaterialUsedRepository;
import com.uade.tpo.api_grupo4.repository.PendingUserRepository;
import com.uade.tpo.api_grupo4.repository.RecipeRepository;
import com.uade.tpo.api_grupo4.repository.ReviewRepository;
import com.uade.tpo.api_grupo4.repository.StepRepository;
import com.uade.tpo.api_grupo4.repository.StudentRepository;
import com.uade.tpo.api_grupo4.repository.TypeOfRecipeRepository;
import com.uade.tpo.api_grupo4.repository.UnitRepository;
import com.uade.tpo.api_grupo4.repository.UserRepository;
import com.uade.tpo.api_grupo4.service.EmailService;
import com.uade.tpo.api_grupo4.service.JwtService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Controlador {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final CourseScheduleRepository courseSchedRepository;
	private final HeadquarterRepository headquarterRepository;
	private final CourseAttendRepository courseAttendRepository;
	private final InscripcionRepository inscripcionRepository;
	private final RecipeRepository recipeRepository;
	private final UnitRepository unitRepository;
	private final MaterialUsedRepository materialUsedRepository;
	private final IngredientRepository ingredientRepository;
	private final TypeOfRecipeRepository typeOfRecipeRepository;
	private final StepRepository stepRepository;
	private final ReviewRepository reviewRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PendingUserRepository pendingUserRepository;
	private final EmailService emailService;

	@PersistenceContext
    private EntityManager entityManager;
	//-----------------------------------------------Metodos Publicos--------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean aliasExists(String alias) {
		return userRepository.existsByUsername(alias) || studentRepository.existsByUsername(alias);
	}

	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email) || studentRepository.existsByEmail(email);
	}

	//-----------------------------------------------Registro y Login--------------------------------------------------------------------------------------------------------------------------------------------------------

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

//--------Iniciar Registro--------
	public void iniciarRegistro(RegisterRequest request) throws UserException {
		if (aliasExists(request.getUsername())) {
			throw new UserException("El nombre de usuario '" + request.getUsername() + "' ya está en uso.");
		}
		if (emailExists(request.getEmail())) {
			throw new UserException("El correo electrónico '" + request.getEmail() + "' ya está registrado.");
		}

		String code = String.format("%04d", new Random().nextInt(10000));

		PendingUser pendingUser = PendingUser.builder()
				.username(request.getUsername()).email(request.getEmail()).password(request.getPassword())
				.firstName(request.getFirstName()).lastName(request.getLastName()).phone(request.getPhone())
				.address(request.getAddress()).urlAvatar(request.getUrlAvatar())
				.permissionGranted(request.getPermissionGranted())
				.cardNumber(request.getCardNumber()).nroTramite(request.getNroTramite())
				.nroDocumento(request.getNroDocumento()).tipoTarjeta(request.getTipoTarjeta())
				.dniFrente(request.getDniFrente()).dniDorso(request.getDniDorso())
				.verificationCode(code)
				.expiryDate(LocalDateTime.now().plusMinutes(15))
				.build();

		pendingUserRepository.save(pendingUser);

		emailService.sendVerificationCode(pendingUser.getEmail(), code);
	}

	//--------Finalizar Registro--------
	public void finalizarRegistro(String email, String code) throws UserException {
		PendingUser pendingUser = pendingUserRepository.findById(email)
				.orElseThrow(() -> new UserException("No se encontró un registro pendiente para este email. Puede que haya expirado."));

		if (pendingUser.getExpiryDate().isBefore(LocalDateTime.now())) {
			pendingUserRepository.delete(pendingUser);
			throw new UserException("El código de verificación ha expirado. Por favor, intenta registrarte de nuevo.");
		}

		if (!pendingUser.getVerificationCode().equals(code)) {
			throw new UserException("El código de verificación es incorrecto.");
		}

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

		crearUsuarioGeneral(finalRequest);

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

	@Transactional
    public Student upgradeUserToStudent(Person person, BecomeStudentRequest request) throws Exception {
        // Verificamos que la persona que hace la petición sea realmente un User
        if (!(person instanceof User)) {
            throw new Exception("La operación no es válida: la cuenta ya es de un estudiante.");
        }
        
        Long personId = person.getId();

        // 1. Eliminamos la fila de la tabla 'user' usando una consulta SQL nativa.
        entityManager.createNativeQuery("DELETE FROM user WHERE id = :personId")
                .setParameter("personId", personId)
                .executeUpdate();

        // 2. Insertamos la nueva fila en la tabla 'student' con los datos correspondientes.
        entityManager.createNativeQuery("INSERT INTO student (id, card_number, dni_frente, dni_dorso, nro_tramite, cuenta_corriente, nro_documento, tipo_tarjeta) VALUES (:id, :cardNumber, :dniFrente, :dniDorso, :nroTramite, :cuentaCorriente, :nroDocumento, :tipoTarjeta)")
                .setParameter("id", personId)
                .setParameter("cardNumber", request.getCardNumber())
                .setParameter("dniFrente", request.getDniFrente())
                .setParameter("dniDorso", request.getDniDorso())
                .setParameter("nroTramite", request.getNroTramite())
                .setParameter("cuentaCorriente", 0)
                .setParameter("nroDocumento", request.getNroDocumento())
                .setParameter("tipoTarjeta", request.getTipoTarjeta())
                .executeUpdate();
        
        // ▼▼▼ ¡CAMBIO CLAVE! ▼▼▼
        // 3. Actualizamos el flag 'permissionGranted' directamente en la tabla 'person'.
        entityManager.createNativeQuery("UPDATE person SET permission_granted = false WHERE id = :personId")
                .setParameter("personId", personId)
                .executeUpdate();

        // 4. Forzamos la ejecución de las consultas y limpiamos la caché de la sesión.
        entityManager.flush();
        entityManager.clear();
        
        // 5. Devolvemos el objeto Student recién constituido, buscándolo de nuevo para asegurar que refleje el estado final.
        return studentRepository.findById(personId)
                .orElseThrow(() -> new Exception("Error crítico al reconvertir el usuario a estudiante."));
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

	//-----------------------------------------------Recetas--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//--------Ingredientes--------
    public Ingredient createIngredient(Ingredient ingredientData) {
        String normalizedName = ingredientData.getName().trim().toLowerCase();

        Optional<Ingredient> existingIngredient = ingredientRepository.findByName(normalizedName);

        if (existingIngredient.isPresent()) {
            throw new IllegalStateException("El ingrediente '" + normalizedName + "' ya existe.");
        } else {
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(normalizedName);
            return ingredientRepository.save(newIngredient);
        }
    }

	//--------Materiales usados--------

	public MaterialUsed addMaterialToRecipe(Long recipeId, MaterialRequestDTO materialRequest) throws Exception {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("No se encontró la receta con ID: " + recipeId));

        Ingredient ingredient = ingredientRepository.findById(materialRequest.getIngredientId())
                .orElseThrow(() -> new Exception("No se encontró el ingrediente con ID: " + materialRequest.getIngredientId()));

        Unit unit = null;
        if (materialRequest.getUnitId() != null) {
            unit = unitRepository.findById(materialRequest.getUnitId())
                    .orElseThrow(() -> new Exception("No se encontró la unidad con ID: " + materialRequest.getUnitId()));
        }

        MaterialUsed newMaterial = MaterialUsed.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .quantity(materialRequest.getQuantity())
                .unity(unit)
                .observation(materialRequest.getObservation())
                .build();
        
        return materialUsedRepository.save(newMaterial);
    }

	//--------Crear Tipo de receta--------
	public TypeOfRecipe createTypeOfRecipe(CreateTypeRequest request) throws Exception {
		String typeName = request.getName().trim();

		if (typeOfRecipeRepository.findByNameIgnoreCase(typeName).isPresent()) {
			throw new Exception("El tipo de receta '" + typeName + "' ya existe.");
		}

		TypeOfRecipe newType = TypeOfRecipe.builder()
				.name(typeName)
				.build();

		return typeOfRecipeRepository.save(newType);
	}


	//--------Crear Tipo de unidad--------

	public Unit createUnit(CreateUnitRequest request) throws Exception {
		String unitDescription = request.getDescription().trim();

		if (unitRepository.findByDescriptionIgnoreCase(unitDescription).isPresent()) {
			throw new Exception("La unidad '" + unitDescription + "' ya existe.");
		}

		Unit newUnit = Unit.builder()
				.description(unitDescription)
				.build();

		return unitRepository.save(newUnit);
	}

	//--------Crear receta--------

	@Transactional
	public Recipe createRecipeWithMaterials(CreateRecipeRequest request, Person author) throws Exception {
		
		TypeOfRecipe typeOfRecipe = typeOfRecipeRepository.findById(request.getTypeOfRecipeId())
				.orElseThrow(() -> new Exception("Tipo de receta no encontrado con ID: " + request.getTypeOfRecipeId()));

		Recipe newRecipe = Recipe.builder()
				.recipeName(request.getRecipeName())
				.user(author)
				.mainPicture(request.getMainPicture())
				.servings(request.getServings())
				.comensales(request.getCantidadPersonas())
				.typeOfRecipe(typeOfRecipe)
				.ingredients(new ArrayList<>())
				.description(new ArrayList<>())
				.reviews(new ArrayList<>())
				.descriptionGeneral(request.getDescriptionGeneral())
				.build();

		if (request.getIngredients() != null) {
			for (MaterialRequestDTO materialDto : request.getIngredients()) {
				
				Ingredient ingredientToUse;

				if (materialDto.getIngredientId() != null) {
					ingredientToUse = ingredientRepository.findById(materialDto.getIngredientId())
							.orElseThrow(() -> new Exception("Ingrediente no encontrado con el ID proporcionado: " + materialDto.getIngredientId()));
				
				} else if (materialDto.getIngredientName() != null && !materialDto.getIngredientName().trim().isEmpty()) {
					String ingredientName = materialDto.getIngredientName().trim().toLowerCase();
					
					ingredientToUse = ingredientRepository.findByName(ingredientName)
							.orElseGet(() -> {
								Ingredient newIngredient = Ingredient.builder().name(ingredientName).build();
								return ingredientRepository.save(newIngredient);
							});

				} else {
					throw new Exception("Se debe proporcionar 'ingredientId' o 'ingredientName' para cada material.");
				}

				Unit unit = materialDto.getUnitId() != null ? unitRepository.findById(materialDto.getUnitId())
						.orElseThrow(() -> new Exception("Unidad no encontrada con ID: " + materialDto.getUnitId())) : null;

				MaterialUsed material = MaterialUsed.builder()
						.recipe(newRecipe)
						.ingredient(ingredientToUse)
						.unity(unit)
						.quantity(materialDto.getQuantity())
						.observation(materialDto.getObservation())
						.build();
				newRecipe.getIngredients().add(material);
			}
		}
		
		if (request.getSteps() != null) {
			for (StepRequestDTO stepDto : request.getSteps()) {
				Step step = Step.builder()
						.recipe(newRecipe)
						.numberOfStep(stepDto.getNumberOfStep())
						.comment(stepDto.getComment())
						.imagenPaso(stepDto.getImagenPaso())
						.videoPaso(stepDto.getVideoPaso())
						.build();
				newRecipe.getDescription().add(step);
			}
		}

		return recipeRepository.save(newRecipe);
	}
		
	//--------Review--------
	public Review createReview(Long recipeId, CreateReviewRequest request, Person author) throws Exception {
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new Exception("No se encontró la receta con ID: " + recipeId));

		Review newReview = Review.builder()
				.recipe(recipe)
				.user(author)
				.rating(request.getRating())
				.comment(request.getComment())
				.build();

		return reviewRepository.save(newReview);
	}

	//-----------------------------------------------Busquedas--------------------------------------------------------------------------------------------------------------------------------------------------------

	//--------Todas las recetas--------
	public Page<Recipe> findAllRecipes(String sortOrder, int page, int size) {
		Sort sort;
		switch (sortOrder.toLowerCase()) {
			case "newest":
				sort = Sort.by("id").descending();
				break;
			case "oldest":
				sort = Sort.by("id").ascending();
				break;
			case "alpha_asc":
			default:
				sort = Sort.by("recipeName").ascending();
				break;
		}

		Pageable pageable = PageRequest.of(page, size, sort);

		return recipeRepository.findAll(pageable);
	}

	private Pageable createPageable(String sortOrder, int page, int size) {
        Sort sort;
        switch (sortOrder.toLowerCase()) {
            case "newest":
                sort = Sort.by("id").descending();
                break;
            case "oldest":
                sort = Sort.by("id").ascending();
                break;
            case "alpha_asc":
            default:
                sort = Sort.by("recipeName").ascending();
                break;
        }
        return PageRequest.of(page, size, sort);
    }

	//--------Todas las unidades--------
	public List<Unit> findAllUnits() {
        return unitRepository.findAll(Sort.by("description").ascending());
    }

	//--------Recetas por nombre--------
	public Page<Recipe> findRecipesByName(String name, String sortOrder, int page, int size) {
        Pageable pageable = createPageable(sortOrder, page, size);
        return recipeRepository.findByRecipeNameContainingIgnoreCase(name, pageable);
    }

	//--------Recetas por nombre de usuario--------
	public Page<Recipe> findRecipesByAuthor(String authorUsername, String sortOrder, int page, int size) {
        Pageable pageable = createPageable(sortOrder, page, size);
        return recipeRepository.findByUserUsernameContainingIgnoreCase(authorUsername, pageable);
    }

	//--------Recetas por tipo--------
	public Page<Recipe> findRecipesByType(Long typeId, String sortOrder, int page, int size) {
        Pageable pageable = createPageable(sortOrder, page, size);
        
        return recipeRepository.findByTypeOfRecipeId(typeId, pageable);
    }

	//--------Todas los tipos de recetas--------
	public List<TypeOfRecipe> findAllTypes() {
		return typeOfRecipeRepository.findAll(Sort.by("name").ascending());
	}

	//--------Recetas por ingrediente o NO ingrediente--------
	public Page<Recipe> findRecipesByIngredientPresence(String ingredientName, boolean contains, String sortOrder, int page, int size) {
		Pageable pageable = createPageable(sortOrder, page, size);
		
		if (contains) {
			return recipeRepository.findRecipesWithIngredient(ingredientName, pageable);
		} else {
			return recipeRepository.findRecipesWithoutIngredient(ingredientName, pageable);
		}
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

      
    public Headquarter updateSede(Headquarter headquarter) throws Exception {
          try {
            if (!headquarterRepository.existsById(headquarter.getId())) 
              throw new HeadquarterException("la sede con id: '" + headquarter.getId() + "' no existe.");
            
            Headquarter updatedSede = headquarterRepository.save(headquarter);
            return updatedSede;
          } catch (HeadquarterException error) {
            throw new HeadquarterException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[Controlador.updateSede] -> " + error.getMessage());
          }
    }

	public Headquarter saveSede(Headquarter headquarter) throws Exception {
		try{
        Headquarter sede = headquarterRepository.save(headquarter);   
        return sede;

		} catch (Exception error) {
            throw new Exception("[Controlador.saveSede] -> " + error.getMessage());
          }
        }
    

	@Transactional
    public void deleteSede(Long id) throws Exception {
          try {
              headquarterRepository.deleteById(id);
          } catch (Exception error) {
            throw new Exception("[Controlador.deleteSede] -> " + error.getMessage());
          }
    }

	public void cargarSede(Long sedeId, Long courseId){
		Headquarter sedeSeleccionada = headquarterRepository.findById(sedeId).orElseThrow(() -> new HeadquarterException("La sede con id " + sedeId + " no existe."));
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseException("Curso no encontrado"));
		
		List<Headquarter> sedes = new ArrayList<>();
		List<Headquarter> existeListaSedes = course.getSedes();
		if( existeListaSedes == null){
			sedes.add(sedeSeleccionada);
			course.setSedes(sedes);
		} else existeListaSedes.add(sedeSeleccionada);
		courseRepository.save(course);
		
		courseRepository.save(course);
	}
	
	//-----------------------------------------------Courses--------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<Course> todosLosCursos() throws CourseException {
			List<Course> cursos = courseRepository.findAll();
			if (cursos.isEmpty()) {
				throw new CourseException("No se encontraron cursos en la base de datos.");
			}
			return cursos;
	}
	

	public Course getCourseById(Long courseId) throws Exception {
		try{
			return courseRepository.findById(courseId).orElseThrow(() -> new CourseException("Curso no encontrado"));
		} catch (CourseException error) {
			throw new CourseException(error.getMessage());
		} catch (Exception error) {
			throw new Exception("[Controlador.getCourseByName] -> " + error.getMessage());
		}
	}

	public Course createCourse(Course course) throws Exception {

          try {
			
			Course createdCourse = courseRepository.save(course);
            return createdCourse;
          } catch (Exception error) {
            throw new Exception("[Controlador.createCourse] -> " + error.getMessage());
          }
        }

	public void inicializarCursos() throws Exception {
		try{	

            Course course1 = new Course(null, "Cocina Vegana", "Familiarizate con la cocina vegana.", "No necesitas conocimientos previos.", 120, 600, CourseMode.MIXTO, "2025-08-08", "2025-11-08", new ArrayList<>());
            Course cours2 = new Course(null, "Cocina Asiática", "Comprendé técnicas básicas clave.", "Conocimientos básicos de cocina.", 180, 800, CourseMode.PRESENCIAL,  "2025-08-08", "2025-11-08", new ArrayList<>());
            Course course3 = new Course(null, "Reposteria Cacera", "Aprendé a conocer sobre decoracion.", "Material de reposteria.", 120, 400, CourseMode.VIRTUAL,  "2025-08-08", "2025-11-08", new ArrayList<>());
          
            courseRepository.save(course1); 
			courseRepository.save(cours2);
			courseRepository.save(course3);

		 } catch (CourseException error) {

        	throw new CourseException(error.getMessage());
      } catch (Exception error) {
				throw new Exception("[Controlador.inicializarCursos] -> " + error.getMessage());
			}
    	}

	public Course updateCourse(Course course) throws Exception {
          try {
            if (!courseRepository.existsById(course.getId())) 
              throw new CourseException("El curso con id: '" + course.getId() + "' no existe.");
            
            Course updatedCourse = courseRepository.save(course);
            return updatedCourse;
          } catch (CourseException error) {
            throw new CourseException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[Controlador.updateCourse] -> " + error.getMessage());
          }
    }

	@Transactional
    public void deleteCourse(Long id) throws Exception {
          try {
              courseRepository.deleteById(id);
          } catch (Exception error) {
            throw new Exception("[Controlador.deleteCourse] -> " + error.getMessage());
          }
    }
    
    public List<CourseView> findByMode(CourseMode mode) {
        return courseRepository.findByMode(mode)
                .stream()
                .map(Course::toView)
                .collect(Collectors.toList());
    }
    
    public List<CourseView> findByName(String name) {
        return courseRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(Course::toView)
                .collect(Collectors.toList());
    }

	//-----------------------------------------------CourseSchedule--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public CourseSchedule saveCronograma(long courseId, Long sedeId, CourseSchedule schedule) throws Exception {
      try{
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseException("Curso no encontrado"));
        schedule.setCourse(course);
		Headquarter sedeSeleccionada = headquarterRepository.findById(sedeId).orElseThrow(() -> new HeadquarterException("Sede no encontrada"));
		
		List<Headquarter> listaSedesDeCurso = course.getSedes();
		if(listaSedesDeCurso.contains(sedeSeleccionada))
		schedule.setSede(sedeSeleccionada);

		CourseSchedule schedulecreated = courseSchedRepository.save(schedule);   
        return schedulecreated;

		} catch (Exception error) {
            throw new Exception("[Controlador.createCourse] -> " + error.getMessage());
          }
    }

    public CourseSchedule updateCronograma(CourseSchedule courseSchedule) throws Exception {
          try {
            if (!courseSchedRepository.existsById(courseSchedule.getId())) 
              throw new CourseScheduleException("El cronograma con id: '" + courseSchedule.getId() + "' no existe.");
            
            CourseSchedule updatedCourseSched = courseSchedRepository.save(courseSchedule);
            return updatedCourseSched;
          } catch (CourseScheduleException error) {
            throw new CourseScheduleException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[Controlador.updateCourseSchedule] -> " + error.getMessage());
          }
    }
    

	@Transactional
    public void deleteCourseSchedule(Long id) throws Exception {
          try {
              
			  courseSchedRepository.findById(id).orElseThrow(() -> new CourseScheduleException("El cronograma con id " + id + " no existe."));
			  courseSchedRepository.deleteById(id);
			
          } catch (Exception error) {
            throw new Exception("[Controlador.deleteCourseSchedule] -> " + error.getMessage());
          }
        }

	public List<CourseScheduleView> findSchedByCourse(Long courseId) {
        return courseSchedRepository.findByCourseId(courseId)
                .stream()
                .map(CourseSchedule::toView)
                .collect(Collectors.toList());
    	}

	
//----------------------------------Inscripciones----------------------------------------------------------//
	@Transactional
    public InscripcionView enrollStudent(Long studentId, Long courseScheduleId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        
        CourseSchedule courseSchedule = courseSchedRepository.findById(courseScheduleId)
                .orElseThrow(() -> new IllegalArgumentException("cronograma no encontrado"));
        
		
        // Verificar que el estudiante no esté ya inscripto en el curso
        Optional<Inscripcion> existingInscription = inscripcionRepository
                .findByStudentAndCourseSchedule(student, courseSchedule);
        
        if (existingInscription.isPresent() && 
            "ACTIVA".equals(existingInscription.get().getEstado())) {
            throw new IllegalArgumentException("El estudiante ya está inscripto en este curso");
        }
        
        // Verificar disponibilidad de cupos
        Long activeEnrollments = inscripcionRepository.countActiveByCourseSchedule(courseScheduleId);
        // Aquí podrías agregar lógica para verificar cupos disponibles basado en cronogramas
        
        Inscripcion inscripcion = Inscripcion.builder()
                .student(student)
                .courseSchedule(courseSchedule)
                .fechaInscripcion(LocalDateTime.now())
                .estado("ACTIVA")
                .build();
		
        Inscripcion savedInscripcion = inscripcionRepository.save(inscripcion);
        return mapToView(savedInscripcion);
    }
    
	
	@Transactional
    public Optional<InscripcionView> cancelEnrollment(Long inscripcionId) {
        return inscripcionRepository.findById(inscripcionId)
                .map(inscripcion -> {
                    inscripcion.setEstado("CANCELADA");
                    return mapToView(inscripcionRepository.save(inscripcion));
                });
    }
    
    public List<InscripcionView> findByStudent(Long studentId) {
        return inscripcionRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToView)
                .collect(Collectors.toList());
    }

	private InscripcionView mapToView(Inscripcion inscripcion) {
        return new InscripcionView(
                inscripcion.getId(),
                inscripcion.getStudent(),
                inscripcion.getCourseSchedule(),
                inscripcion.getFechaInscripcion(),
                inscripcion.getEstado(),
                inscripcion.getAsistencias()
        );
    }


	//-----------------------------------------------CourseAttended--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	

	
}